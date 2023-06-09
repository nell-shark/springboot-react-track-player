package com.nellshark.musicplayer.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private final HttpStatus status;
    private final String error;
    private final String path;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;

    public ErrorResponse(HttpStatus status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.statusCode = status.value();
    }
}
