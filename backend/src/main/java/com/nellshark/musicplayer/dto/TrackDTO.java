package com.nellshark.musicplayer.dto;

import java.util.UUID;

public record TrackDTO(UUID id,
                       String name,
                       byte[] bytes) {
}
