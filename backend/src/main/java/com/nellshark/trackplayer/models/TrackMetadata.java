package com.nellshark.trackplayer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
public class TrackMetadata {
    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "seconds", nullable = false, columnDefinition = "INT")
    private Integer seconds;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public TrackMetadata(String name, Integer seconds) {
        this.name = name;
        this.seconds = seconds;
        this.timestamp = LocalDateTime.now();
    }

    public TrackMetadata(String name, Integer seconds, @Nullable LocalDateTime timestamp) {
        this.name = name;
        this.seconds = seconds;
        this.timestamp = Objects.nonNull(timestamp) ? timestamp : LocalDateTime.now();
    }
}
