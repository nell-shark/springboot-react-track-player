package com.nellshark.musicplayer.configs;

import com.nellshark.musicplayer.models.Track;
import com.nellshark.musicplayer.repositories.TrackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    public CommandLineRunner initDatabase(TrackRepository trackRepository) {
        log.info("Starting database initialization");
        return args -> {
            for (long i = 0; i < 30; i++) {
                UUID uuid = UUID.randomUUID();
                Track track = Track.builder()
                        .id(uuid)
                        .name(uuid.toString())
                        .durationSec(i)
                        .build();
                log.info("Loading data: " + trackRepository.save(track));
            }
        };
    }
}
