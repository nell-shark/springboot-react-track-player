package com.nellshark.musicplayer.repositories;

import com.nellshark.musicplayer.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {

}
