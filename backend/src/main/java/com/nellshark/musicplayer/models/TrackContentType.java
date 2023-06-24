package com.nellshark.musicplayer.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TrackContentType {
    MPEG("audio/mpeg"),
    MP3("audio/mp3");

    private final String type;
}
