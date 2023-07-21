package com.nellshark.trackplayer.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private final HttpStatus status;
    private final String error;
    private final String path;
    private final LocalDateTime timestamp;
    private final int statusCode;

    public ErrorResponse(HttpStatus status, String error, String path, LocalDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
        this.statusCode = status.value();
    }
}
