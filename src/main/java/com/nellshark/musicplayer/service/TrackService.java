package com.nellshark.musicplayer.service;

import com.nellshark.musicplayer.model.Track;
import java.util.List;
import java.util.Random;
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

  public List<Track> getAllTracks() {
    return s3Service.getAllObjects(bucketName).stream()
        .map(s3Object -> Track.builder().name(s3Object.key()).durationInSeconds(2L)
            .id(new Random().nextLong())
            .author("Random").build())
        .toList();
  }
}
