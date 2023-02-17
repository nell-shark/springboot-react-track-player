package com.nellshark.musicplayer.controllers;

import com.nellshark.musicplayer.models.Track;
import com.nellshark.musicplayer.services.TrackService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
public class TrackController {

  private final TrackService trackService;

  @GetMapping
  public List<Track> getAllTracks() {
    return trackService.getAllTracks();
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void upload(@RequestParam("file") MultipartFile multipartFile) {
    trackService.upload(multipartFile);
  }
}
