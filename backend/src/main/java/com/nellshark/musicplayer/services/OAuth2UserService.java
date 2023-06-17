package com.nellshark.musicplayer.services;

import com.nellshark.musicplayer.models.AppOAuth2User;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final OAuth2UserRepository OAuth2UserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading oauth2 user: {}", userRequest.getClientRegistration());
        OAuth2User oauth2User = super.loadUser(userRequest);

        Integer id = oauth2User.getAttribute("id");
        AppOAuth2User appOAuth2User = new AppOAuth2User(id);
        saveAppOAuth2User(appOAuth2User);

        return oauth2User;
    }

    public Map<String, String> getUserLoginAndAvatar(OAuth2User principal) {
        log.info("Getting oauth2 login and avatar: {}", principal);
        if (Objects.isNull(principal)) return null;

        Map<String, String> map = new HashMap<>();
        map.put("login", principal.getAttribute("login"));
        map.put("avatarUrl", principal.getAttribute("avatar_url"));

        return map;
    }

    public void saveAppOAuth2User(AppOAuth2User user) {
        log.info("Saving oauth2 user to db");
        OAuth2UserRepository.save(user);
    }
}
