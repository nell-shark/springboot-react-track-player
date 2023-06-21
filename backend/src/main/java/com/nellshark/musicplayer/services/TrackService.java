package com.nellshark.musicplayer.services;

import com.nellshark.musicplayer.configs.S3Buckets;
import com.nellshark.musicplayer.exceptions.FileIsEmptyException;
import com.nellshark.musicplayer.exceptions.FileMustBeTrackException;
import com.nellshark.musicplayer.exceptions.ParseTrackException;
import com.nellshark.musicplayer.exceptions.TrackNotFoundException;
import com.nellshark.musicplayer.models.Track;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final TrackRepository trackRepository;
    private static final List<String> TRACK_CONTENT_TYPES = List.of("audio/mpeg", "audio/mp3");

    public void initTracksTable() {
        log.info("Init tracks");

        List<S3Object> s3Objects = s3Service.getS3ObjectsFromBucket(s3Buckets.getTracks());
        List<Track> tracks = s3Objects.stream()
                .map(s3Object -> {
                    Map<String, String> metadata = s3Service.getMetadata(s3Buckets.getTracks(), s3Object.key());

                    UUID id = UUID.fromString(s3Object.key());
                    String name = metadata.get("name");
                    Integer seconds = Integer.parseInt(metadata.get("seconds"));
                    Instant timestamp = s3Object.lastModified();
                    byte[] bytes = s3Service.getObject(s3Buckets.getTracks(), s3Object.key());

                    return new Track(id, name, seconds, timestamp, bytes);
                })
                .toList();

        trackRepository.saveAll(tracks);
    }

    public List<Track> getTracks() {
        return trackRepository.findAll();
    }

    public Map<String, Object> getTracks(Pageable pageable, String filter) {
        log.info("Getting tracks by page: {}", pageable.getPageNumber());
        Page<Track> page = StringUtils.isBlank(filter)
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
        if (trackFile.isEmpty()) throw new FileIsEmptyException("Cannot upload empty track: " + trackFile);

        if (!TRACK_CONTENT_TYPES.contains(trackFile.getContentType()))
            throw new FileMustBeTrackException("File must be a track");

        Metadata metadata;
        byte[] trackBytes;
        try (InputStream inputStream = new BufferedInputStream(trackFile.getInputStream())) {
            metadata = parseTrackMetadata(inputStream);
            trackBytes = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Track track = new Track(null, trackName, getDurationInSeconds(metadata), Instant.now(), trackBytes);
        saveTrack(track);
    }

    public void uploadTrack(String trackName, File trackFile) {
        log.info("Uploading track: {}", trackName);
        if (trackFile.length() == 0) throw new FileIsEmptyException("Cannot upload empty track: " + trackFile);

        String contentType;
        try {
            contentType = Files.probeContentType(Path.of(trackFile.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!TRACK_CONTENT_TYPES.contains(contentType))
            throw new FileMustBeTrackException("File must be a track");


        Metadata metadata;
        byte[] trackBytes;
        try (InputStream inputStream = new FileInputStream(trackFile)) {
            metadata = parseTrackMetadata(inputStream);
            trackBytes = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Track track = new Track(null, trackName, getDurationInSeconds(metadata), Instant.now(), trackBytes);
        saveTrack(track);
    }

    private int getDurationInSeconds(Metadata metadata) {
        return (int) Double.parseDouble(metadata.get("xmpDM:duration"));
    }

    private Metadata parseTrackMetadata(InputStream inputStream) throws IOException {
        log.info("Parsing track metadata");
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

    public Track getTrackById(UUID id) {
        log.info("Getting track by Id: {}", id);
        return trackRepository
                .findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Track wasn't found: " + id));
    }

    public void saveTrack(Track track) {
        log.info("Saving track: {}", track);
        Track savedTrack = trackRepository.save(track);

        // Upload track to s3
        String key = savedTrack.getId().toString();
        byte[] bytes = savedTrack.getBytes();

        Map<String, String> metadata = new HashMap<>();
        metadata.put("name", savedTrack.getName());
        metadata.put("seconds", savedTrack.getSeconds().toString());

        s3Service.putObject(s3Buckets.getTracks(), key, bytes, metadata);
    }
}
