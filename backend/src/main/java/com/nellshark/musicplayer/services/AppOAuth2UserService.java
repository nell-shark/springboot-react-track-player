package com.nellshark.musicplayer.services;

import com.nellshark.musicplayer.dto.AppOAuth2UserDTO;
import com.nellshark.musicplayer.exceptions.AppOAuth2UserNotFoundException;
import com.nellshark.musicplayer.mappers.AppOAuth2UserDTOMapper;
import com.nellshark.musicplayer.models.AppOAuth2User;
import com.nellshark.musicplayer.models.Track;
import com.nellshark.musicplayer.repositories.AppOAuth2UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppOAuth2UserService extends DefaultOAuth2UserService {
    private final AppOAuth2UserRepository appOAuth2UserRepository;
    private final AppOAuth2UserDTOMapper appOAuth2UserDTOMapper;
    private final TrackService trackService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading AppOAuth2User: {}", userRequest.getClientRegistration());
        OAuth2User oauth2User = super.loadUser(userRequest);

        Integer id = oauth2User.getAttribute("id");
        AppOAuth2User appOAuth2User = new AppOAuth2User(id);
        saveAppOAuth2User(appOAuth2User);

        return oauth2User;
    }

    public AppOAuth2UserDTO getAppOAuth2UserDTO(OAuth2User principal) {
        log.info("Getting login and avatar of OAuth2 user: {}", principal);
        if (Objects.isNull(principal)) return null;

        Integer id = principal.getAttribute("id");
        if (Objects.isNull(id)) throw new NullPointerException("OAuth2User id is null");

        AppOAuth2User appOAuth2User = appOAuth2UserRepository
                .findById(id)
                .orElseThrow(() -> new AppOAuth2UserNotFoundException("AppOAuth2User not found: " + id));

        return appOAuth2UserDTOMapper.toDTO(principal, appOAuth2User); // TODO: remove principal
    }

    public void saveAppOAuth2User(AppOAuth2User user) {
        log.info("Saving AppOAuth2User to db");
        appOAuth2UserRepository.save(user);
    }

    public void addFavoriteTrackToUser(UUID trackId, OAuth2User oauth2User) {
        log.info("Adding favorite track to user: {}", trackId);
        Track track = trackService.getTrackById(trackId);
        Integer id = oauth2User.getAttribute("id");
        AppOAuth2User userById = getAppOauth2UserById(id);
        userById.getFavoriteTracks().add(track);
        saveAppOAuth2User(userById);
    }

    public AppOAuth2User getAppOauth2UserById(Integer id) {
        log.info("Getting OAuth2 user by id: {}", id);
        return appOAuth2UserRepository.findAll()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AppOAuth2UserNotFoundException("User with id " + id + " not found"));
    }
}
