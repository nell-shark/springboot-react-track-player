package com.nellshark.musicplayer.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @GetMapping("/info")
    public Map<String, String> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (Objects.isNull(principal)) return null;

        return Map.of("login", Objects.requireNonNull(principal.getAttribute("login")),
                "avatarUrl", Objects.requireNonNull(principal.getAttribute("avatar_url")));
    }

    @PostMapping("/oauth/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (Objects.nonNull(session)) session.invalidate();
    }
}
