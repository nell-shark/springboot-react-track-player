package com.nellshark.musicplayer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Track {

  private Long id;

  @NonNull
  private String name;

  private String author;

  private Long durationInSeconds;

  private Byte[] data;
}
