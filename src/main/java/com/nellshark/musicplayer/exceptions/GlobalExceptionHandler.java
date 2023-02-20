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
  public ResponseEntity<ErrorResponse> handleFileIsEmptyException(HttpServletRequest request,
      Exception exception) {
    log.error(exception.getClass().getSimpleName() + " Occurred: " + exception.getMessage());

    return buildResponseEntity(HttpStatus.NO_CONTENT, request, exception);
  }

  @ExceptionHandler(FileMustBeTrackException.class)
  public ResponseEntity<ErrorResponse> handleFileMustBeTrackException(HttpServletRequest request,
      Exception exception) {
    log.error(exception.getClass().getSimpleName() + " Occurred: " + exception.getMessage());

    return buildResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE, request, exception);
  }

  private ResponseEntity<ErrorResponse> buildResponseEntity(
      HttpStatus httpStatus,
      HttpServletRequest request,
      Exception exception) {
    ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), exception.getMessage(),
        request.getRequestURL());

    return ResponseEntity
        .status(httpStatus)
        .body(errorResponse);
  }
}
