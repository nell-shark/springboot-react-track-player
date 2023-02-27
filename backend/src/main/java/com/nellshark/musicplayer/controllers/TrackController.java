package com.nellshark.musicplayer.controllers;

import com.nellshark.musicplayer.models.Track;
import com.nellshark.musicplayer.services.TrackService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/tracks")
@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
@Slf4j
public class TrackController {

  private final TrackService trackService;

  @GetMapping
  public List<Track> getAllTracks(@RequestParam(value = "search", required = false) String search) {
    return trackService.searchTracks(search);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void uploadTrack(@RequestParam("name") String name, @RequestParam("file") MultipartFile file)
      throws IOException {
    log.warn(name);
    log.warn(file.getName());
//    trackService.upload(name, file);
  }
}
