package com.nellshark.musicplayer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tracks")
@Data
@NoArgsConstructor
public class Track {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "seconds", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Long seconds;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @ManyToMany(mappedBy = "favoriteTracks")
    private Set<AppOAuth2User> users;

    public Track(UUID id, String name, Long seconds, LocalDateTime timestamp) {
        this.id = id;
        this.name = name;
        this.seconds = seconds;
        this.timestamp = timestamp;
        this.users = new HashSet<>();
    }
}
