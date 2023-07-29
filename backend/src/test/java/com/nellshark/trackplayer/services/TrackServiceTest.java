package com.nellshark.trackplayer.services;

import com.nellshark.trackplayer.configs.S3Buckets;
import com.nellshark.trackplayer.dto.TrackDTO;
import com.nellshark.trackplayer.exceptions.TrackNotFoundException;
import com.nellshark.trackplayer.mappers.TrackDTOMapper;
import com.nellshark.trackplayer.models.Track;
import com.nellshark.trackplayer.models.TrackListPage;
import com.nellshark.trackplayer.models.TrackMetadata;
import com.nellshark.trackplayer.repositories.TrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrackServiceTest {
    @Mock
    private S3Service s3Service;

    @Mock
    private S3Buckets s3Buckets;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private TrackDTOMapper trackDTOMapper;

    private TrackService underTest;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = Mockito.spy(new TrackService(s3Service, s3Buckets, trackRepository, trackDTOMapper));
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void initTracksTable_ShouldInit() {
        String bucket = "bucket";
        S3Object s3Object = S3Object.builder().build();
        List<S3Object> s3Objects = List.of(s3Object);
        Track track = new Track(UUID.randomUUID(), new TrackMetadata("name", 1));

        when(s3Buckets.getTracks()).thenReturn(bucket);
        when(s3Service.getS3ObjectsFromBucket(eq(bucket))).thenReturn(s3Objects);

        doReturn(track).when(underTest).convertS3ObjectToTrack(eq(s3Object));
        doNothing().when(underTest).saveTrackToDb(eq(track));

        underTest.initTracksTable();

        verify(s3Buckets).getTracks();
        verify(s3Service).getS3ObjectsFromBucket(eq(bucket));
        verify(underTest).convertS3ObjectToTrack(eq(s3Object));
        verify(underTest).saveTrackToDb(eq(track));
    }

    @Test
    void getAllTracks_ShouldReturnListOfTracks() {
        Track track = new Track(UUID.randomUUID(), new TrackMetadata("name", 1));
        List<Track> list = List.of(track);

        when(trackRepository.findAll()).thenReturn(list);

        List<Track> result = underTest.getAllTracks();

        assertThat(result).isEqualTo(list);

        verify(trackRepository).findAll();
    }

    @Test
    void getTrackListPage_ShouldReturnTrackListPage() {
        int page = 1;
        Track track = new Track(UUID.randomUUID(), new TrackMetadata("name", 1));
        TrackDTO trackDTO = new TrackDTO(null, track.getMetadata().getName());
        List<Track> trackList = List.of(track);
        Page<Track> trackPage = new PageImpl<>(trackList);

        when(trackRepository.findAll(any(Pageable.class))).thenReturn(trackPage);
        when(trackDTOMapper.toDTO(any(Track.class))).thenReturn(trackDTO);

        TrackListPage trackListPage = underTest.getTrackListPage(page, null);

        assertThat(trackListPage.tracks().get(0)).isEqualTo(trackDTO);

        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(trackRepository).findAll(pageableArgumentCaptor.capture());
        Pageable pageableArgumentCaptorValue = pageableArgumentCaptor.getValue();
        verify(trackRepository).findAll(eq(pageableArgumentCaptorValue));
        verify(trackRepository, never()).search(any(), any());
        verify(trackDTOMapper, times(trackList.size())).toDTO(any(Track.class));
    }


    @Test
    void getTrackById_ShouldReturnTrack() {
        String bucket = "bucket";
        UUID trackId = UUID.randomUUID();
        byte[] bytes = "Hello world".getBytes();
        TrackMetadata metadata = new TrackMetadata("name", 1);
        Track expectedTrack = new Track(trackId, metadata, bytes);

        when(s3Buckets.getTracks()).thenReturn(bucket);
        when(trackRepository.findById(eq(trackId))).thenReturn(Optional.of(expectedTrack));
        when(s3Service.getObject(eq(bucket), eq(trackId.toString()))).thenReturn(bytes);

        Track resultTrack = underTest.getTrackById(trackId);

        assertThat(resultTrack).isEqualTo(expectedTrack);
        assertThat(resultTrack.getBytes()).isEqualTo(expectedTrack.getBytes());

        verify(s3Buckets).getTracks();
        verify(trackRepository).findById(eq(trackId));
        verify(s3Service).getObject(eq(bucket), eq(trackId.toString()));
    }

    @Test
    void getTrackById_ShouldThrowException() {
        UUID invalidTrackId = UUID.randomUUID();

        when(trackRepository.findById(eq(invalidTrackId))).thenReturn(Optional.empty());

        assertThrows(TrackNotFoundException.class, () -> underTest.getTrackById(invalidTrackId));
    }

    @Test
    void saveTrackToDb() {
        Track track = new Track(UUID.randomUUID(), new TrackMetadata("name", 1));

        when(trackRepository.save(eq(track))).thenReturn(track);

        underTest.saveTrackToDb(track);

        verify(trackRepository).save(eq(track));
    }

    @Test
    void saveTrackToS3() {
        Track track = new Track(UUID.randomUUID(), new TrackMetadata("name", 1));

        underTest.saveTrackToS3(track);

        verify(s3Service).putObject(eq(s3Buckets.getTracks()),
                eq(track.getId().toString()),
                eq(track.getBytes()),
                anyMap());
    }
}
