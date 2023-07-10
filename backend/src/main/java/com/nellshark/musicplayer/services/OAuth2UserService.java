package com.nellshark.musicplayer.services;

import com.nellshark.musicplayer.dto.AppOAuth2UserDTO;
import com.nellshark.musicplayer.exceptions.AppOAuth2UserNotFoundException;
import com.nellshark.musicplayer.mappers.AppOAuth2UserDTOMapper;
import com.nellshark.musicplayer.models.AppOAuth2User;
import com.nellshark.musicplayer.models.Track;
import com.nellshark.musicplayer.repositories.OAuth2UserRepository;
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
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final OAuth2UserRepository oauth2UserRepository;
    private final TrackService trackService;
    private final AppOAuth2UserDTOMapper appOAuth2UserDTOMapper;

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
        if (Objects.isNull(principal)) throw new NullPointerException("AppOAuth2User is null");

        Integer id = principal.getAttribute("id");
        if (Objects.isNull(id)) throw new NullPointerException("OAuth2User id is null");

        AppOAuth2User appOAuth2User = oauth2UserRepository
                .findById(id)
                .orElseThrow(() -> new AppOAuth2UserNotFoundException("AppOAuth2User not found: " + id));

        return appOAuth2UserDTOMapper.toDTO(principal, appOAuth2User);
    }

    public void saveAppOAuth2User(AppOAuth2User user) {
        log.info("Saving AppOAuth2User to db");
        oauth2UserRepository.save(user);
    }

    public void addFavoriteTrackToUser(OAuth2User oauth2User, UUID trackId) {
        log.info("Adding favorite track to user: {}", trackId);
        Track track = trackService.getTrackById(trackId);
        Integer id = oauth2User.getAttribute("id");
        AppOAuth2User userById = getAppOauth2UserById(id);
        userById.getFavoriteTracks().add(track);
        saveAppOAuth2User(userById);
    }

    public AppOAuth2User getAppOauth2UserById(Integer id) {
        log.info("Getting OAuth2 user by id: {}", id);
        return oauth2UserRepository.findAll()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AppOAuth2UserNotFoundException("User with id " + id + " not found"));
    }
}
