package com.nellshark.musicplayer.configs;

import com.nellshark.musicplayer.services.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class LoadDatabase {
    private final ApplicationContext applicationContext;

    @Bean
    public CommandLineRunner initDatabase(TrackService trackService) {
        log.info("Starting s3 initialization");
        return args -> {
            trackService.initTracksTable();

            // First init
            if (trackService.getTracks().isEmpty()) {
                List<File> files = getSampleTrackFiles();
                files.forEach(file -> trackService.uploadTrack(file.getName(), file));
            }
        };
    }

    private List<File> getSampleTrackFiles() {
        try {
            List<File> files = new ArrayList<>();
            Resource[] resources = applicationContext.getResources("classpath:samples/*.mp3");
            for (Resource resource : resources) {
                files.add(resource.getFile());
            }
            return files;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
