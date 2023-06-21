package com.nellshark.musicplayer.controllers;

import com.nellshark.musicplayer.services.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/oauth2")
public class OAuth2UserController {
    private final OAuth2UserService oauth2UserService;

    @GetMapping("/info")
    public Map<String, String> getOAuth2UserInfo(@AuthenticationPrincipal OAuth2User principal) {
        return oauth2UserService.getOAuth2UserInfo(principal);
    }

    @PostMapping("/favorite/track")
    public void addFavoriteTrackToUser(@AuthenticationPrincipal OAuth2User oauth2User,
                                       @RequestBody Map<String, UUID> trackId) {
        oauth2UserService.addFavoriteTrackToUser(oauth2User, trackId.get("trackId"));
    }
}
