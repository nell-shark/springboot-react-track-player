package com.nellshark.trackplayer.repositories;

import com.nellshark.trackplayer.AbstractTestcontainers;
import com.nellshark.trackplayer.models.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TrackRepositoryTest extends AbstractTestcontainers {
    @Autowired
    private TrackRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void search_ShouldReturnPageOfTracks() {
        Track track1 = Track.builder().name("ABC SoNg XYZ").seconds(1).build();
        Track track2 = Track.builder().name("Song").seconds(1).build();
        Track track3 = Track.builder().name("My Favorite track").seconds(1).build();
        underTest.saveAll(List.of(track1, track2, track3));

        String searchTerm = "song";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));
        Page<Track> result = underTest.search(searchTerm, pageable);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo(track1.getName());
        assertThat(result.getContent().get(1).getName()).isEqualTo(track2.getName());
    }
}
