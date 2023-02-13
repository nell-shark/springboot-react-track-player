package com.nellshark.musicplayer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Track {

  private Long id;

  @NonNull
  private String name;

  private String author;

  private Long durationInSeconds;

  private Byte[] data;
}
