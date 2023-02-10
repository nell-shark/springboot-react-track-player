package com.nellshark.musicplayer.service;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

  private final S3Client s3Client;


  public void upload(String bucketName, MultipartFile file) {
    try {
      log.info("Uploading a track to S3 - {}", file);
      s3Client.putObject(
          PutObjectRequest.builder()
              .bucket(bucketName)
              .key(file.getName())
              .contentType(file.getContentType())
              .build(),
          RequestBody.fromBytes(file.getBytes()));
    } catch (SdkServiceException | SdkClientException ase) {
      throw ase;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public byte[] getObject(String bucketName, String keyName) {
    try {
      log.info("Retrieving file from S3 for key: {}/{}", bucketName, keyName);
      ResponseBytes<GetObjectResponse> s3Object = s3Client.getObject(
          GetObjectRequest.builder().bucket(bucketName).key(keyName).build(),
          ResponseTransformer.toBytes());
      return s3Object.asByteArray();
    } catch (SdkClientException | SdkServiceException ase) {
      throw ase;
    }
  }

  public List<String> getAllObjects(String bucketName) {
    ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();
    ListObjectsV2Response response = s3Client.listObjectsV2(request);

    return response.contents().stream()
        .map(S3Object::toString)
        .toList();
  }
}
