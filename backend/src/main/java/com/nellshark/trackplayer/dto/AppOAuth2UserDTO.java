package com.nellshark.trackplayer.dto;

import com.nellshark.trackplayer.models.Track;

import java.util.List;

public record AppOAuth2UserDTO(String login,
                               String avatarUrl,
                               List<Track> favoriteTracks) {
}
