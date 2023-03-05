package com.nellshark.musicplayer.controllers;

import com.nellshark.musicplayer.models.Track;
import com.nellshark.musicplayer.services.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tracks")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
@Slf4j
public class TrackController {

    private final TrackService trackService;

    @GetMapping
    public List<Track> getAllTracks(@PageableDefault(sort = "name") Pageable pageable,
                                    @RequestParam(value = "filter", required = false) String filter) {
        return trackService.getAllTracks(pageable, filter);
    }

    @GetMapping("{id}")
    public Track getTrackById(@PathVariable("id") UUID id) {
        return trackService.getTrackById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UUID uploadTrack(
            @RequestParam("name") String name,
            @RequestParam("duration") Long durationSec,
            @RequestParam("file") MultipartFile file) {
        return trackService.upload(name, durationSec, file);
    }
}
