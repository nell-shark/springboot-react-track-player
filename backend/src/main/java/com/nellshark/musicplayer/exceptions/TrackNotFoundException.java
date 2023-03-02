package com.nellshark.musicplayer.exceptions;

public class TrackNotFoundException extends RuntimeException {
    public TrackNotFoundException(String message) {
        super(message);
    }
}
