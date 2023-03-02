package com.nellshark.musicplayer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Optional;
import java.util.UUID;

@Table
@Entity(name = "tracks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Track {

  @Id
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Column(name = "id", nullable = false, updatable = false, columnDefinition = ("VARCHAR(36)"))
  @NonNull
  private UUID id;

  @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
  @NonNull
  private String name;

  @Column(name = "author", columnDefinition = "VARCHAR(255)")
  private String author;

  @Column(name = "duration_sec", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
  @NonNull
  private Long durationSec;

  public Optional<String> getAuthor() {
    return Optional.ofNullable(author);
  }
}