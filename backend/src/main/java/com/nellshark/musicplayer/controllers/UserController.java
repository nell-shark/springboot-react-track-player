package com.nellshark.musicplayer.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @GetMapping("/user-info")
    public Map<String, String> getUserLogin(@AuthenticationPrincipal OAuth2User principal) {
        if (Objects.isNull(principal)) return null;

        return Map.of("login", Objects.requireNonNull(principal.getAttribute("login")),
                "avatarUrl", Objects.requireNonNull(principal.getAttribute("avatar_url")));
    }
}
