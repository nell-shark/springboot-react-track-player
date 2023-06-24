package com.nellshark.musicplayer.configs;

import com.nellshark.musicplayer.services.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
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
        log.info("Starting initialization database");
        return args -> {
            trackService.initTracksTable();

            if (!trackService.getTrackInfoList().isEmpty()) return;

            // First init
            List<File> files = getSampleTrackFiles();
            files.stream()
                    .map(file -> {
                        try {
                            return new MockMultipartFile(
                                    file.getName(),
                                    file.getName(),
                                    "audio/mpeg",
                                    new FileInputStream(file));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .forEach(multipartFile -> trackService.uploadTrack(multipartFile.getName(), multipartFile));
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
