package com.nellshark.trackplayer.exceptions;

public class FileIsEmptyException extends RuntimeException {
  public FileIsEmptyException(String message) {
    super(message);
  }
}
