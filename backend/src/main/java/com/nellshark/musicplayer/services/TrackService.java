package com.nellshark.musicplayer.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nellshark.musicplayer.configs.S3Buckets;
import com.nellshark.musicplayer.exceptions.FileIsEmptyException;
import com.nellshark.musicplayer.exceptions.FileMustBeTrackException;
import com.nellshark.musicplayer.exceptions.ParseTrackException;
import com.nellshark.musicplayer.exceptions.TrackNotFoundException;
import com.nellshark.musicplayer.models.TrackContentType;
import com.nellshark.musicplayer.models.TrackInfo;
import com.nellshark.musicplayer.models.TrackMetadata;
import com.nellshark.musicplayer.models.TrackS3;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final TrackRepository trackRepository;

    public void initTracksTable() {
        log.info("Init tracks table");

        List<S3Object> s3Objects = s3Service.getS3ObjectsFromBucket(s3Buckets.getTracks());
        List<TrackInfo> tracks = s3Objects.stream()
                .map(s3Object -> {
                            TrackMetadata metadata = convertS3ObjectToTrackMetadata(s3Object);
                            return TrackInfo.builder()
                                    .id(UUID.fromString(s3Object.key()))
                                    .name(metadata.name())
                                    .seconds(metadata.seconds())
                                    .timestamp(metadata.timestamp())
                                    .build();
                        }
                )
                .toList();

        trackRepository.saveAll(tracks);
    }

    public List<TrackInfo> getTrackInfoList() {
        log.info("Getting tracks");
        return trackRepository.findAll();
    }

    public Map<String, Object> getTrackInfoListByPage(Pageable pageable, String filter) {
        log.info("Getting tracks by page: {}", pageable.getPageNumber());

        Page<TrackInfo> page = StringUtils.isBlank(filter)
                ? trackRepository.findAll(pageable)
                : trackRepository.search(filter, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("tracks", page.getContent());
        response.put("currentPage", page.getNumber() + 1);
        response.put("hasNext", page.hasNext());

        return response;
    }

    public void uploadTrack(String trackName, MultipartFile trackFile) {
        log.info("Uploading track: {}", trackName);

        checkTrackFileIsValid(trackFile);

        Metadata tikaMetadata;
        byte[] trackBytes;

        try (InputStream inputStream = trackFile.getInputStream()) {
            tikaMetadata = getTikaMetadataFromTrackInputStream(inputStream);
            trackBytes = trackFile.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Integer seconds = getDurationInSecondsFromMetadata(tikaMetadata);
        Instant timestamp = Instant.now();

        TrackInfo trackInfo = TrackInfo.builder()
                .name(trackName)
                .seconds(seconds)
                .timestamp(timestamp)
                .build();
        UUID id = saveTrackInfo(trackInfo);

        TrackMetadata metadata = new TrackMetadata(trackName, seconds, timestamp);
        TrackS3 trackS3 = new TrackS3(id, trackBytes, metadata);
        saveTrackS3(trackS3);
    }

    private void checkTrackFileIsValid(MultipartFile trackFile) {
        log.info("Validation track file");

        if (Objects.isNull(trackFile) || trackFile.isEmpty())
            throw new FileIsEmptyException("Cannot upload empty track: " + trackFile);

        if (Stream.of(TrackContentType.values())
                .map(TrackContentType::getType)
                .noneMatch(contentType -> contentType.equals(trackFile.getContentType())))
            throw new FileMustBeTrackException("File must be a track");
    }

    private int getDurationInSecondsFromMetadata(Metadata metadata) {
        log.info("Getting seconds from track metadata");
        return (int) Double.parseDouble(metadata.get("xmpDM:duration"));
    }

    private Metadata getTikaMetadataFromTrackInputStream(InputStream inputStream) throws IOException {
        log.info("Parsing tika metadata from track");

        try {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext parseContext = new ParseContext();
            Mp3Parser mp3Parser = new Mp3Parser();
            mp3Parser.parse(inputStream, handler, metadata, parseContext);

            return metadata;
        } catch (TikaException | SAXException e) {
            throw new ParseTrackException("Failed to parse track metadata");
        }
    }

    public TrackInfo getTrackInfoById(UUID id) {
        log.info("Getting track info by Id: {}", id);
        return trackRepository
                .findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Track wasn't found: " + id));
    }

    public UUID saveTrackInfo(TrackInfo track) {
        log.info("Saving track info to db: {}", track);
        return trackRepository.save(track).getId();
    }

    public void saveTrackS3(TrackS3 track) {
        log.info("Saving track to S3: {}", track);

        Map<String, String> metadata = convertTrackMetadataToMap(track.trackMetadata());

        s3Service.putObject(s3Buckets.getTracks(), track.id().toString(), track.bytes(), metadata, null);
    }

    private Map<String, String> convertTrackMetadataToMap(TrackMetadata metadata) {
        log.info("Converting track metadata to Map<String, String>");
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.convertValue(metadata, new TypeReference<>() {
        });
    }

    private TrackMetadata convertS3ObjectToTrackMetadata(S3Object s3Object) {
        log.info("Converting S3Object to TrackMetadata");
        Map<String, String> metadata = s3Service.getMetadata(s3Buckets.getTracks(), s3Object.key());

        String name = metadata.get("name");
        Integer seconds = Integer.parseInt(metadata.get("seconds"));
        Instant timestamp = s3Object.lastModified();

        return new TrackMetadata(name, seconds, timestamp);
    }
}
