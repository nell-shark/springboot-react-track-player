package com.nellshark.musicplayer.exceptions;

public class FileIsEmptyException extends RuntimeException {
  public FileIsEmptyException(String message) {
    super(message);
  }
}
