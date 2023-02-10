package com.nellshark.musicplayer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class TrackService {

  @Value("${amazon.bucket.name}")
  private String bucketName;
  private final S3Service s3Service;

  public void upload(MultipartFile file) {
    s3Service.upload(bucketName, file);
  }

  public List<String> getAllTracks() {
    return s3Service.getAllObjects(bucketName);
  }
}
