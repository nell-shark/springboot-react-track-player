package com.nellshark.musicplayer.services;

import com.nellshark.musicplayer.exceptions.FileIsEmptyException;
import com.nellshark.musicplayer.exceptions.FileMustBeTrackException;
import com.nellshark.musicplayer.exceptions.TrackNotFoundException;
import com.nellshark.musicplayer.models.Track;
import com.nellshark.musicplayer.repositories.TrackRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public UUID uploadTrack(String trackName, Long seconds, MultipartFile file) throws IOException {
        log.info("Uploading track: " + file);

        if (file.isEmpty()) throw new FileIsEmptyException("Cannot upload empty file: " + file);

        if (!Set.of("audio/mpeg", "audio/mp3").contains(file.getContentType()))
            throw new FileMustBeTrackException("File must be a track");

        UUID id = UUID.randomUUID();
        s3Service.putObject(bucketName, id.toString(), file.getBytes());

        Track track = new Track(id, trackName, seconds);
        trackRepository.save(track);

        return id;
    }

    public Map<String, Object> getTracks(Pageable pageable, String filter) {
        log.info("Getting tracks by page: " + pageable.getPageNumber());

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
        log.info("Getting track by Id: " + id);

        return trackRepository
                .findById(id)
                .orElseThrow(() -> new TrackNotFoundException("Track wasn't found: " + id));
    }
}
