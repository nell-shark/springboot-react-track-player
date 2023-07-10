package com.nellshark.musicplayer.services;

import com.nellshark.musicplayer.configs.S3Buckets;
import com.nellshark.musicplayer.dto.TrackDTO;
import com.nellshark.musicplayer.exceptions.FileIsEmptyException;
import com.nellshark.musicplayer.exceptions.FileMustBeTrackException;
import com.nellshark.musicplayer.exceptions.ParseTrackException;
import com.nellshark.musicplayer.exceptions.TrackNotFoundException;
import com.nellshark.musicplayer.mappers.TrackDTOMapper;
import com.nellshark.musicplayer.models.Track;
import com.nellshark.musicplayer.models.TrackListPage;
import com.nellshark.musicplayer.repositories.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {
    public static final String TRACK_CONTENT_TYPE = "audio/mpeg";

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final TrackRepository trackRepository;
    private final TrackDTOMapper trackDTOMapper;

    public void initTracksTable() {
        log.info("Init tracks table");

        List<S3Object> s3Objects = s3Service.getS3ObjectsFromBucket(s3Buckets.getTracks());
        s3Objects.stream()
                .map(this::convertS3ObjectToTrack)
                .forEach(this::saveTrackToDb);
    }

    public List<Track> getAllTracks() {
        log.info("Getting tracks");
        return trackRepository.findAll();
    }

    public TrackListPage getTrackListPage(Integer page, String filter) {
        log.info("Getting tracks by page: {}", page);

        if (Objects.isNull(page)) page = 1;

        Sort sort = Sort.by("timestamp").descending();
        final int PAGE_SIZE = 10;
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);

        Page<Track> trackListPage = StringUtils.isBlank(filter)
                ? trackRepository.findAll(pageable)
                : trackRepository.search(filter, pageable);

        return new TrackListPage(
                page,
                trackListPage.hasNext(),
                trackListPage.getContent()
                        .stream()
                        .map(trackDTOMapper::toDTO)
                        .toList()
        );
    }

    public void uploadTrack(String trackName, MultipartFile trackFile) {
        log.info("Uploading track: {}", trackName);

        checkTrackFileIsValid(trackFile);

        Metadata tikaMetadata = getTikaMetadataFromMultipartFile(trackFile);
        byte[] trackBytes = getBytesFromMultipartFile(trackFile);

        UUID id = UUID.randomUUID();
        Integer seconds = getTrackDurationFromTikaMetadata(tikaMetadata);
        LocalDateTime timestamp = LocalDateTime.now();

        Track track = Track.builder()
                .id(id)
                .name(trackName)
                .seconds(seconds)
                .timestamp(timestamp)
                .bytes(trackBytes)
                .build();

        saveTrackToS3(track);
        saveTrackToDb(track);
    }

    public TrackDTO getTrackDTOById(UUID id) {
        log.info("Getting track dto by Id: {}", id);
        Track track = trackRepository
                .findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Track not found: " + id));

        byte[] bytes = s3Service.getObject(s3Buckets.getTracks(), track.getId().toString());
        track.setBytes(bytes);

        return trackDTOMapper.toDTO(track);
    }

    public Track getTrackById(UUID id) {
        log.info("Getting track by Id: {}", id);
        Track track = trackRepository
                .findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Track not found: " + id));

        byte[] bytes = s3Service.getObject(s3Buckets.getTracks(), track.getId().toString());
        track.setBytes(bytes);

        return track;
    }

    public void saveTrackToDb(Track track) {
        log.info("Saving track to db: {}", track);
        trackRepository.save(track);
    }

    public void saveTrackToS3(Track track) {
        log.info("Saving track to S3: {}", track);

        Map<String, String> metadata = Map.of(
                "name", track.getName(),
                "seconds", track.getSeconds().toString(),
                "timestamp", track.getTimestamp().toString()
        );

        s3Service.putObject(s3Buckets.getTracks(),
                track.getId().toString(),
                track.getBytes(),
                metadata);
    }

    private void checkTrackFileIsValid(MultipartFile trackFile) {
        log.info("Validation track file");

        if (Objects.isNull(trackFile) || trackFile.isEmpty())
            throw new FileIsEmptyException("Cannot upload empty track: " + trackFile);

        if (!Objects.equals(trackFile.getContentType(), TRACK_CONTENT_TYPE))
            throw new FileMustBeTrackException("File must be a track");
    }

    private int getTrackDurationFromTikaMetadata(Metadata metadata) {
        log.info("Getting seconds from tika metadata");
        return (int) Double.parseDouble(metadata.get("xmpDM:duration"));
    }

    private Metadata getTikaMetadataFromMultipartFile(MultipartFile trackFile) {
        log.info("Parsing tika metadata from track");

        try (InputStream inputStream = trackFile.getInputStream()) {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext parseContext = new ParseContext();
            Mp3Parser mp3Parser = new Mp3Parser();
            mp3Parser.parse(inputStream, handler, metadata, parseContext);

            return metadata;
        } catch (TikaException | SAXException e) {
            throw new ParseTrackException("Failed to parse track metadata");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getBytesFromMultipartFile(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Track convertS3ObjectToTrack(S3Object s3Object) {
        log.info("Converting S3Object to Track");
        Map<String, String> metadata = s3Service.getMetadata(s3Buckets.getTracks(), s3Object.key());

        return Track.builder()
                .id(UUID.fromString(s3Object.key()))
                .name(metadata.get("name"))
                .seconds(Integer.parseInt(metadata.get("seconds")))
                .timestamp(LocalDateTime.parse(metadata.get("timestamp")))
                .build();
    }
}
