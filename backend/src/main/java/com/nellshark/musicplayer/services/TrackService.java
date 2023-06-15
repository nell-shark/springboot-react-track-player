package com.nellshark.musicplayer.services;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {
    @Value("${amazon.s3.buckets.tracks}")
    private String bucketName;
    private final S3Service s3Service;
    private final TrackRepository trackRepository;
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("audio/mpeg", "audio/mp3");

    public void uploadTrack(String trackName, MultipartFile file) {
        log.info("Uploading track");
        if (file.isEmpty()) throw new FileIsEmptyException("Cannot upload empty track: " + file);

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType()))
            throw new FileMustBeTrackException("File must be a track");

        Metadata metadata;
        byte[] trackBytes;
        try (InputStream inputStream = new BufferedInputStream(file.getInputStream())) {
            metadata = parseTrackMetadata(inputStream);
            trackBytes = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UUID id = UUID.randomUUID();
        long seconds = (long) Double.parseDouble(metadata.get("xmpDM:duration"));
        s3Service.putObject(bucketName, id.toString(), trackBytes);
        saveTrack(new Track(id, trackName, seconds, LocalDateTime.now()));
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

    public Track getTrackById(UUID id) {
        log.info("Getting track by Id: {}", id);
        return trackRepository
                .findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Track wasn't found: " + id));
    }

    public void saveTrack(Track track) {
        log.info("Saving track: {}", track.getId());
        trackRepository.save(track);
    }
}
