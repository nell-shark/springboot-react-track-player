package com.nellshark.trackplayer.controllers;

import com.nellshark.trackplayer.models.Track;
import com.nellshark.trackplayer.models.TrackListPage;
import com.nellshark.trackplayer.services.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tracks")
@RequiredArgsConstructor
@Slf4j
public class TrackController {
    private final TrackService trackService;

    @GetMapping
    public ResponseEntity<TrackListPage> getTrackListPage(
            @RequestParam("page") int page,
            @RequestParam(value = "filter", required = false) String filter) {
        TrackListPage trackListPage = trackService.getTrackListPage(page, filter);
        return ResponseEntity.ok(trackListPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable("id") UUID id) {
        Track track = trackService.getTrackById(id);
        return ResponseEntity.ok().body(track);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadTrack(@RequestParam("name") @NotBlank String name,
                                            @RequestParam("file") @NotNull MultipartFile file) {
        trackService.uploadTrack(name, file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
