package com.nellshark.trackplayer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tracks")
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private LocalDateTime timestamp = LocalDateTime.now();

    @Transient
    @ToString.Exclude
    private byte[] bytes;

    @ManyToMany(mappedBy = "favoriteTracks", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<AppOAuth2User> users = new ArrayList<>();
}

