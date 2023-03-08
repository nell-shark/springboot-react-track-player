package com.nellshark.musicplayer.repositories;

import com.nellshark.musicplayer.models.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query("SELECT track " +
            "FROM  Track track " +
            "WHERE LOWER(track.name)   LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR    LOWER(track.author) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Track> search(@Param("filter") String filter, Pageable pageable);
}
