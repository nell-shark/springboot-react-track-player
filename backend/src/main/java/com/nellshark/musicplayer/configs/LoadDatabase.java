package com.nellshark.musicplayer.configs;

import com.nellshark.musicplayer.models.Track;
import com.nellshark.musicplayer.repositories.TrackRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {
  @Bean
  public CommandLineRunner initDatabase(TrackRepository trackRepository){
    log.info("Starting database initialization");
    Track track1 = Track.builder().name("Example").build();
    Track track2 = Track.builder().name("Test").author("NellShark").build();
    Track track3 = Track.builder().name("Track").author("Lil peep").durationSeconds(32L).build();
    Track track4 = Track.builder().name("Sound").durationSeconds(123L).build();
    Track track5 = Track.builder().name("Music").build();
    List<Track> trackList = List.of(track1, track2, track3, track4, track5);


    return args -> {
      log.info("Loading data :" + trackRepository.saveAll(trackList));
    };
  }
}
