package com.nellshark.trackplayer.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class S3ServiceTest {
    @Mock
    private S3Client s3Client;

    private S3Service underTest;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new S3Service(s3Client);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void putObject_ShouldBeSuccess() throws IOException {
        String bucket = "bucket";
        String key = "key";
        byte[] file = "Hello World".getBytes();
        Map<String, String> metadata = new HashMap<>();

        underTest.putObject(bucket, key, file, metadata);

        ArgumentCaptor<PutObjectRequest> putObjectRequestArgumentCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);
        ArgumentCaptor<RequestBody> requestBodyArgumentCaptor = ArgumentCaptor.forClass(RequestBody.class);

        verify(s3Client).putObject(
                putObjectRequestArgumentCaptor.capture(),
                requestBodyArgumentCaptor.capture()
        );

        PutObjectRequest putObjectRequestArgumentCaptorValue = putObjectRequestArgumentCaptor.getValue();

        assertThat(putObjectRequestArgumentCaptorValue.bucket()).isEqualTo(bucket);
        assertThat(putObjectRequestArgumentCaptorValue.key()).isEqualTo(key);

        RequestBody requestBodyArgumentCaptorValue = requestBodyArgumentCaptor.getValue();

        assertThat(requestBodyArgumentCaptorValue.contentStreamProvider().newStream().readAllBytes())
                .isEqualTo(RequestBody.fromBytes(file).contentStreamProvider().newStream().readAllBytes());
    }

    @Test
    void getObject_ShouldReturnByteArray() throws IOException {
        String bucket = "bucket";
        String key = "key";
        byte[] data = "Hello World".getBytes();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        @SuppressWarnings("unchecked")
        ResponseInputStream<GetObjectResponse> response = mock(ResponseInputStream.class);

        when(response.readAllBytes()).thenReturn(data);
        when(s3Client.getObject(eq(getObjectRequest))).thenReturn(response);

        byte[] bytes = underTest.getObject(bucket, key);

        assertThat(bytes).isEqualTo(data);

        verify(response).readAllBytes();
        verify(s3Client).getObject(eq(getObjectRequest));
    }

    @Test
    void getS3ObjectsFromBucket_ShouldReturnListS3Objects() {
        String bucket = "bucket";
        S3Object s3Object = S3Object.builder().build();
        List<S3Object> s3Objects = List.of(s3Object);

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucket)
                .build();

        ListObjectsV2Response response = mock(ListObjectsV2Response.class);

        when(s3Client.listObjectsV2(eq(request))).thenReturn(response);
        when(response.contents()).thenReturn(s3Objects);

        List<S3Object> result = underTest.getS3ObjectsFromBucket(bucket);

        assertThat(result).isEqualTo(s3Objects);

        verify(s3Client).listObjectsV2(eq(request));
        verify(response).contents();
    }


    @Test
    void getMetadata_ShouldReturnMap() {
        String bucket = "bucket";
        String key = "key";
        Map<String, String> map = Map.of("key", "value");

        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        HeadObjectResponse response = mock(HeadObjectResponse.class);

        when(s3Client.headObject(eq(headObjectRequest))).thenReturn(response);
        when(response.metadata()).thenReturn(map);

        Map<String, String> metadata = underTest.getMetadata(bucket, key);

        assertThat(metadata).isEqualTo(map);

        verify(s3Client).headObject(eq(headObjectRequest));
        verify(response).metadata();
    }
}
