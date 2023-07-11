package com.nellshark.musicplayer.controllers;

import com.nellshark.musicplayer.dto.AppOAuth2UserDTO;
import com.nellshark.musicplayer.services.AppOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/oauth2")
public class AppOAuth2UserController {
    private final AppOAuth2UserService appOAuth2UserService;

    @GetMapping
    public ResponseEntity<AppOAuth2UserDTO> getAppOAuth2User(@AuthenticationPrincipal OAuth2User principal) {
        AppOAuth2UserDTO user = appOAuth2UserService.getAppOAuth2UserDTO(principal);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/favorite/track/{trackId}")
    public ResponseEntity<Void> addFavoriteTrackToUser(@PathVariable("trackId") UUID id,
                                                       @AuthenticationPrincipal OAuth2User oauth2User) {
        appOAuth2UserService.addFavoriteTrackToUser(id, oauth2User);
        return ResponseEntity.ok().build();
    }
}
