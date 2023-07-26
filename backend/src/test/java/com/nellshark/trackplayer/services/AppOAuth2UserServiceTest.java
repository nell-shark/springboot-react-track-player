package com.nellshark.trackplayer.services;

import com.nellshark.trackplayer.dto.AppOAuth2UserDTO;
import com.nellshark.trackplayer.exceptions.AppOAuth2UserNotFoundException;
import com.nellshark.trackplayer.mappers.AppOAuth2UserDTOMapper;
import com.nellshark.trackplayer.models.AppOAuth2User;
import com.nellshark.trackplayer.models.Track;
import com.nellshark.trackplayer.repositories.AppOAuth2UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppOAuth2UserServiceTest {
    @Mock
    private AppOAuth2UserRepository appOAuth2UserRepository;

    @Mock
    private AppOAuth2UserDTOMapper appOAuth2UserDTOMapper;

    @Mock
    private TrackService trackService;

    private AppOAuth2UserService underTest;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = Mockito.spy(new AppOAuth2UserService(appOAuth2UserRepository, appOAuth2UserDTOMapper, trackService));
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void getAppOAuth2UserDTO_ShouldReturnAppOAuth2UserDTO() {
        int userId = 1;
        OAuth2User oauth2User = Mockito.mock(OAuth2User.class);
        AppOAuth2User appOAuth2User = new AppOAuth2User(userId);
        AppOAuth2UserDTO appOAuth2UserDTO = new AppOAuth2UserDTO("John", "url", List.of());

        when(oauth2User.getAttribute(eq("id"))).thenReturn(userId);
        when(appOAuth2UserRepository.findById(eq(userId))).thenReturn(Optional.of(appOAuth2User));
        when(appOAuth2UserDTOMapper.toDTO(eq(oauth2User), eq(appOAuth2User))).thenReturn(appOAuth2UserDTO);

        AppOAuth2UserDTO result = underTest.getAppOAuth2UserDTO(oauth2User);

        assertNotNull(result);
        assertSame(appOAuth2UserDTO, result);
        verify(appOAuth2UserRepository).findById(eq(userId));
        verify(appOAuth2UserDTOMapper).toDTO(eq(oauth2User), eq(appOAuth2User));
    }

    @Test
    public void getAppOAuth2UserDTO_ShouldThrowException() {
        int userId = 123;
        OAuth2User oauth2User = Mockito.mock(OAuth2User.class);

        when(oauth2User.getAttribute(eq("id"))).thenReturn(userId);
        when(appOAuth2UserRepository.findById(eq(userId))).thenReturn(Optional.empty());

        assertThrows(AppOAuth2UserNotFoundException.class, () -> underTest.getAppOAuth2UserDTO(oauth2User));
        verify(appOAuth2UserRepository).findById(eq(userId));
        verify(appOAuth2UserDTOMapper, never()).toDTO(any(), any());
    }

    @Test
    public void addFavoriteTrackToUser_ShouldBeSuccess() {
        int userId = 1;
        OAuth2User oauth2User = Mockito.mock(OAuth2User.class);
        AppOAuth2User appOAuth2User = new AppOAuth2User(userId);
        UUID trackId = UUID.randomUUID();
        Track track = Track.builder().id(trackId).name("name").seconds(1).build();

        when(trackService.getTrackById(eq(trackId))).thenReturn(track);
        when(oauth2User.getAttribute(eq("id"))).thenReturn(userId);
        doReturn(appOAuth2User).when(underTest).getAppOauth2UserById(eq(userId));
        doNothing().when(underTest).saveAppOAuth2UserToDb(eq(appOAuth2User));

        underTest.addFavoriteTrackToUser(trackId, oauth2User);

        assertTrue(appOAuth2User.getFavoriteTracks().contains(track));
        verify(trackService).getTrackById(eq(trackId));
        verify(underTest).getAppOauth2UserById(eq(userId));
        verify(underTest).saveAppOAuth2UserToDb(eq(appOAuth2User));
    }

    @Test
    void removeUserFavoriteTrack_ShouldRemoveTrack() {
        int userId = 123;
        OAuth2User oauth2User = Mockito.mock(OAuth2User.class);
        AppOAuth2User appOAuth2User = new AppOAuth2User(userId);
        UUID trackId = UUID.randomUUID();
        Track track = Track.builder().id(trackId).name("name").seconds(1).build();
        appOAuth2User.getFavoriteTracks().add(track);

        when(trackService.getTrackById(eq(trackId))).thenReturn(track);
        when(oauth2User.getAttribute(eq("id"))).thenReturn(userId);
        doReturn(appOAuth2User).when(underTest).getAppOauth2UserById(eq(userId));
        doNothing().when(underTest).saveAppOAuth2UserToDb(eq(appOAuth2User));

        underTest.removeUserFavoriteTrack(trackId, oauth2User);

        assertFalse(appOAuth2User.getFavoriteTracks().contains(track));
        verify(trackService).getTrackById(eq(trackId));
        verify(underTest).getAppOauth2UserById(eq(userId));
        verify(underTest).saveAppOAuth2UserToDb(eq(appOAuth2User));
    }

    @Test
    void getAppOauth2UserById_ShouldReturnUser() {
        int firstUserId = 123;
        int secondsUserId = 321;
        AppOAuth2User firstUser = new AppOAuth2User(firstUserId);
        AppOAuth2User secondUser = new AppOAuth2User(secondsUserId);
        List<AppOAuth2User> userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(secondUser);

        when(appOAuth2UserRepository.findAll()).thenReturn(userList);

        AppOAuth2User result = underTest.getAppOauth2UserById(firstUserId);

        assertNotNull(result);
        assertSame(firstUser, result);
        verify(appOAuth2UserRepository).findAll();
    }

    @Test
    void getAppOauth2UserById_ShouldThrowException() {
        int userId = 123;
        List<AppOAuth2User> userList = new ArrayList<>();

        when(appOAuth2UserRepository.findAll()).thenReturn(userList);

        assertThrows(AppOAuth2UserNotFoundException.class, () -> underTest.getAppOauth2UserById(userId));
        verify(appOAuth2UserRepository).findAll();
    }
}
