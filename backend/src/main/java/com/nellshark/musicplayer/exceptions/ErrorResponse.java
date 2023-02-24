package com.nellshark.musicplayer.exceptions;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ErrorResponse {

  private final LocalDateTime timestamp = LocalDateTime.now();
  private final int status;
  private final String error;
  private final StringBuffer path;
}
