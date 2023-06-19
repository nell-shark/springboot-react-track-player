package com.nellshark.musicplayer.services;

import com.nellshark.musicplayer.exceptions.AppOauth2UserNotFoundException;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final OAuth2UserRepository oauth2UserRepository;
    private final TrackService trackService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading OAuth2 user: {}", userRequest.getClientRegistration());
        OAuth2User oauth2User = super.loadUser(userRequest);

        Integer id = oauth2User.getAttribute("id");
        AppOAuth2User appOAuth2User = new AppOAuth2User(id);
        saveAppOAuth2User(appOAuth2User);

        return oauth2User;
    }

    public Map<String, String> getUserLoginAndAvatar(OAuth2User principal) {
        log.info("Getting OAuth2 login and avatar: {}", principal);
        if (Objects.isNull(principal)) return null;

        Map<String, String> map = new HashMap<>();
        map.put("login", principal.getAttribute("login"));
        map.put("avatarUrl", principal.getAttribute("avatar_url"));

        return map;
    }

    public void saveAppOAuth2User(AppOAuth2User user) {
        log.info("Saving OAuth2 user to db");
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
                .orElseThrow(() -> new AppOauth2UserNotFoundException("User with id " + id + " not found"));
    }
}
