package com.nellshark.trackplayer.dto;

import com.nellshark.trackplayer.models.Track;

import java.util.Set;

public record AppOAuth2UserDTO(String login,
                               String avatarUrl,
                               Set<Track> favoriteTracks) {
}
