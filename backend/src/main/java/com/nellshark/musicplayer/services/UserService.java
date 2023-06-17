package com.nellshark.musicplayer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    public Map<String, String> getUserLoginAndAvatar(OAuth2User principal) {
        if (Objects.isNull(principal)) return null;

        Map<String, String> map = new HashMap<>();
        map.put("login", principal.getAttribute("login"));
        map.put("avatarUrl", principal.getAttribute("avatar_url"));

        return map;
    }
}
