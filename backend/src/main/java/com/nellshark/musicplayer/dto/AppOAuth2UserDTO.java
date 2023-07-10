package com.nellshark.musicplayer.dto;

import com.nellshark.musicplayer.models.Track;

import java.util.Set;

public record AppOAuth2UserDTO(String login,
                               String avatarUrl,
                               Set<Track> favoriteTracks) {
}
