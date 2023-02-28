package com.nellshark.musicplayer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Optional;
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

  @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
  @NonNull
  private String name;

  @Column(name = "author", columnDefinition = "VARCHAR(255)")
  private String author;

  @Column(name = "duration_sec", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  @NonNull
  private Long durationSeconds;

  public Optional<String> getAuthor() {
    return Optional.ofNullable(author);
  }
}
