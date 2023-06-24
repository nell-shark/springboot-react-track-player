package com.nellshark.musicplayer.models;

import java.util.UUID;

public record TrackS3(UUID id,
                      byte[] bytes,
                      TrackMetadata trackMetadata) {
}
