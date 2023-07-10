package com.nellshark.musicplayer.controllers;

import com.nellshark.musicplayer.dto.AppOAuth2UserDTO;
import com.nellshark.musicplayer.services.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class AppOAuth2UserController {
    private final OAuth2UserService oauth2UserService;

    @GetMapping("/info")
    public ResponseEntity<AppOAuth2UserDTO> getAppOAuth2User(@AuthenticationPrincipal OAuth2User principal) {
        AppOAuth2UserDTO user = oauth2UserService.getAppOAuth2UserDTO(principal);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/favorite/track")
    public ResponseEntity<Void> addFavoriteTrackToUser(@AuthenticationPrincipal OAuth2User oauth2User,
                                                       @RequestBody Map<String, UUID> trackId) {
        oauth2UserService.addFavoriteTrackToUser(oauth2User, trackId.get("trackId"));
        return ResponseEntity.ok().build();
    }
}
