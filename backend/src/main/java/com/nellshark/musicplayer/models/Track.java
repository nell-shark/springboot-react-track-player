package com.nellshark.musicplayer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Table
@Entity(name = "tracks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Track {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @NonNull
  @Column(name = "name", nullable = false, columnDefinition = "TEXT(255)")
  private String name;

  @Column(name = "author", columnDefinition = "TEXT(255)")
  private String author;

  @Column(name = "duration_seconds", columnDefinition = "SMALLINT UNSIGNED") // nullable = false
  private Long durationSeconds;

//  @Column(name = "name", nullable = false , columnDefinition = "TEXT")
//  private Byte[] data;
}
