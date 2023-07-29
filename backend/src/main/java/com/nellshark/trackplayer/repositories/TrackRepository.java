package com.nellshark.trackplayer.repositories;

import com.nellshark.trackplayer.models.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TrackRepository extends JpaRepository<Track, UUID> {
    @Query("SELECT track " +
            "FROM  Track track " +
            "WHERE LOWER(track.metadata.name) " +
            "LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<Track> search(@Param("filter") String filter, Pageable pageable);
}
