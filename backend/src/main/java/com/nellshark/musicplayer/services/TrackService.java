package com.nellshark.musicplayer.services;

import com.nellshark.musicplayer.exceptions.FileIsEmptyException;
import com.nellshark.musicplayer.exceptions.FileMustBeTrackException;
import com.nellshark.musicplayer.models.Track;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackService {

  @Value("${amazon.bucket.name}")
  private String bucketName;
  private final S3Service s3Service;

  public void upload(MultipartFile file) {
    log.info("Uploading file: " + file);
    if (file.isEmpty()) {
      throw new FileIsEmptyException("Cannon upload empty file: " + file.getSize());
    }

    if (!Set.of("audio/mpeg", "audio/mp3").contains(file.getContentType())) {
      throw new FileMustBeTrackException("File must be a Track");
    }

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
