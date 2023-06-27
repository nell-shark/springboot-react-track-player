package com.nellshark.musicplayer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tracks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Track {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    @NonNull
    private String name;

    @Column(name = "seconds", nullable = false, columnDefinition = "INT")
    @NonNull
    private Integer seconds;

    @Column(name = "timestamp", nullable = false)
    @Builder.Default
    private Instant timestamp = Instant.now();

    @Transient
    @ToString.Exclude
    private byte[] bytes;

    @ManyToMany(mappedBy = "favoriteTracks")
    private Set<AppOAuth2User> users;
}

