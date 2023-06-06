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

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {
    @Value("${amazon.s3.buckets.tracks}")
    private String bucketName;
    private final S3Service s3Service;
    private final TrackRepository trackRepository;

    public UUID upload(String name, Long durationSec, MultipartFile file) {
        log.info("Uploading file: " + file);

        if (file.isEmpty()) {
            throw new FileIsEmptyException("Cannon upload empty file: " + file.getSize());
        }

        if (!Set.of("audio/mpeg", "audio/mp3").contains(file.getContentType())) {
            throw new FileMustBeTrackException("File must be a Track");
        }

        UUID id = UUID.randomUUID();
        s3Service.upload(bucketName, id.toString(), file);

        Track track = Track.builder()
                .id(id)
                .name(name)
                .seconds(durationSec)
                .build();

        trackRepository.save(track);

        return id;
    }

    public List<Track> getAllTracks() {
        log.info("Getting all tracks");
        return trackRepository.findAll();
    }

    public Map<String, Object> getTracks(Pageable pageable, String filter) {
        log.info("Getting all tracks by page: " + pageable.getPageNumber());

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
