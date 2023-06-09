package com.nellshark.musicplayer.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(FileIsEmptyException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, FileIsEmptyException exception) {
        log.error(exception.getClass().getSimpleName() + " Occurred: " + exception.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NO_CONTENT,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(FileMustBeTrackException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request,
                                                         FileMustBeTrackException exception) {
        log.error(exception.getClass().getSimpleName() + " Occurred: " + exception.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(TrackNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest request, TrackNotFoundException exception) {
        log.error(exception.getClass().getSimpleName() + " Occurred: " + exception.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest request) {
        log.error(exception.getClass().getSimpleName() + " Occurred: " + exception.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
