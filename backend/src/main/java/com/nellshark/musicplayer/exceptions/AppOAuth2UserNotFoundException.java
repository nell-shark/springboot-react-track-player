package com.nellshark.musicplayer.exceptions;

public class AppOAuth2UserNotFoundException extends RuntimeException {
    public AppOAuth2UserNotFoundException(String message) {
        super(message);
    }
}
