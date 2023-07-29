package com.nellshark.trackplayer.repositories;

import com.nellshark.trackplayer.AbstractTestcontainers;
import com.nellshark.trackplayer.models.Track;
import com.nellshark.trackplayer.models.TrackMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

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
        Track track1 = new Track(UUID.randomUUID(), new TrackMetadata("ABC SoNg XYZ", 1));
        Track track2 = new Track(UUID.randomUUID(), new TrackMetadata("sIng", 1));
        Track track3 = new Track(UUID.randomUUID(), new TrackMetadata("My Favorite track", 1));
        underTest.saveAll(List.of(track1, track2, track3));

        String searchTerm = "song";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Track> result = underTest.search(searchTerm, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getMetadata().getName())
                .isEqualTo(track1.getMetadata().getName());
    }
}
