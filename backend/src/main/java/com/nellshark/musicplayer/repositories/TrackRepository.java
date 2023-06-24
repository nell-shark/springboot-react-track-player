package com.nellshark.musicplayer.repositories;

import com.nellshark.musicplayer.models.TrackInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TrackRepository extends JpaRepository<TrackInfo, UUID> {
    @Query("SELECT track " +
            "FROM  TrackInfo track " +
            "WHERE LOWER(track.name) LIKE LOWER(CONCAT('%', :filter, '%'))")
    Page<TrackInfo> search(@Param("filter") String filter, Pageable pageable);
}
