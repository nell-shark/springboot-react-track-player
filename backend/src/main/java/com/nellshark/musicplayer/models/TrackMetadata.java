package com.nellshark.musicplayer.models;

import java.time.Instant;

public record TrackMetadata(String name,
                            Integer seconds,
                            Instant timestamp) {
}
