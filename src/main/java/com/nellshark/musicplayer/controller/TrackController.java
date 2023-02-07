package com.nellshark.musicplayer.controller;

import com.nellshark.musicplayer.service.TrackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/tracks")
@RequiredArgsConstructor
public class TrackController {

  private final TrackService trackService;

  @GetMapping
  public String test() {
    return "test123";
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void upload(@RequestParam("file") MultipartFile multipartFile) {
    trackService.upload(multipartFile);
  }
}
