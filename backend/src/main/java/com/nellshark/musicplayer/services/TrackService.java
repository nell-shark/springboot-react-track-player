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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {

    @Value("${amazon.bucket.name}")
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

    public List<Track> getTracks(Pageable pageable, String filter) {
        log.info("Getting all tracks by page: " + pageable.getPageNumber());
        return StringUtils.isBlank(filter)
                ? trackRepository.findAll(pageable).getContent()
                : searchTracks(pageable, filter);
    }

    public Track getTrackById(UUID id) {
        log.info("Getting track by Id: " + id);
        return trackRepository.findAll()
                .stream()
                .filter(track -> track.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TrackNotFoundException("Track wasn't found: " + id));
    }

    public List<Track> searchTracks(Pageable pageable, String filter) {
        log.info("Searching tracks by filter: " + filter);
        if (StringUtils.isBlank(filter)) {
            return getAllTracks();
        }

        String _filter = filter.trim().toLowerCase();

        return trackRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Track::getName))
                .filter(track -> {
                    boolean searchByName = track.getName().toLowerCase().contains(_filter);
                    boolean searchByAuthor = track.getAuthor().isPresent()
                            && track.getAuthor().get().toLowerCase().contains(_filter);
                    return searchByName || searchByAuthor;
                })
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .toList();
    }
}
