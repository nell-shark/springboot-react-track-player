package com.nellshark.trackplayer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tracks")
@Data
@NoArgsConstructor(force = true)
public class Track {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Embedded
    @JsonUnwrapped
    private TrackMetadata metadata;

    @Transient
    @ToString.Exclude
    private byte[] bytes;

    @ManyToMany(mappedBy = "favoriteTracks", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AppOAuth2User> users = new ArrayList<>();

    public Track(UUID id, TrackMetadata metadata) {
        this.id = Objects.nonNull(id) ? id : UUID.randomUUID();
        this.metadata = metadata;
    }

    public Track(UUID id, TrackMetadata metadata, @Nullable byte[] bytes) {
        this.id = Objects.nonNull(id) ? id : UUID.randomUUID();
        this.metadata = metadata;
        this.bytes = bytes;
    }
}

