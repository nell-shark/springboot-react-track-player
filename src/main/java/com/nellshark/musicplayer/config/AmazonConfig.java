package com.nellshark.musicplayer.config;

import static software.amazon.awssdk.regions.Region.US_WEST_2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AmazonConfig {

  @Value("${amazon.credentials.access.key}")
  private String accessKey;
  @Value("${amazon.credentials.secret.key}")
  private String secretKey;

  @Bean
  public S3Client setUpS3Client() {
    return S3Client.builder()
        .region(US_WEST_2)
        .credentialsProvider(
            StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
        .build();
  }
}
