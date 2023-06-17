package com.nellshark.musicplayer.controllers;

import com.nellshark.musicplayer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public Map<String, String> getUserLoginAndAvatar(@AuthenticationPrincipal OAuth2User principal) {
        return userService.getUserLoginAndAvatar(principal);
    }
}
