package com.nellshark.musicplayer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TrackService {

  private final S3Service s3Service;

  public void upload(MultipartFile file) {
    s3Service.upload(file);
  }

  public List<String> getAllTracks() {
    return s3Service.getAllObjects();
  }
}
