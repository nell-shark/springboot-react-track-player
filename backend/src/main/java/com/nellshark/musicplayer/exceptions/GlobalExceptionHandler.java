package com.nellshark.musicplayer.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(FileIsEmptyException.class)
    public ResponseEntity<ErrorResponse> handleException(FileIsEmptyException e, HttpServletRequest request) {
        log.error(e.getClass().getSimpleName() + " Occurred: " + e.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NO_CONTENT,
                e.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(FileMustBeTrackException.class)
    public ResponseEntity<ErrorResponse> handleException(FileMustBeTrackException e, HttpServletRequest request) {
        log.error(e.getClass().getSimpleName() + " Occurred: " + e.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                e.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(TrackNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(TrackNotFoundException e, HttpServletRequest request) {
        log.error(e.getClass().getSimpleName() + " Occurred: " + e.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(ParseTrackException.class)
    public ResponseEntity<ErrorResponse> handleException(ParseTrackException e, HttpServletRequest request) {
        log.error(e.getClass().getSimpleName() + " Occurred: " + e.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        log.error(e.getClass().getSimpleName() + " Occurred: " + e.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
