package com.nellshark.musicplayer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class AppOAuth2User {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "favorite_tracks",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "track_id", referencedColumnName = "id")
    )
    private Set<Track> favoriteTracks;

    public AppOAuth2User(Integer id) {
        this.id = id;
        this.favoriteTracks = new HashSet<>();
    }
}
