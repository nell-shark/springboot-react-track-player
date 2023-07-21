package com.nellshark.trackplayer.configs;

import com.nellshark.trackplayer.services.TrackService;
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

import static com.nellshark.trackplayer.services.TrackService.TRACK_CONTENT_TYPE;

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

            // First init
            if (!trackService.getAllTracks().isEmpty()) return;
            List<MockMultipartFile> multipartFiles = getSampleTrackFiles();
            multipartFiles.forEach(multipartFile -> trackService.uploadTrack(multipartFile.getName(), multipartFile));
        };
    }

    private List<MockMultipartFile> getSampleTrackFiles() {
        try {
            Resource[] resources = applicationContext.getResources("classpath:samples/*.mp3");
            List<File> files = new ArrayList<>();
            for (Resource resource : resources) files.add(resource.getFile());

            List<MockMultipartFile> multipartFiles = new ArrayList<>();
            for (File file : files) {
                MockMultipartFile multipartFile = new MockMultipartFile(
                        file.getName(),
                        file.getName(),
                        TRACK_CONTENT_TYPE,
                        new FileInputStream(file));
                multipartFiles.add(multipartFile);
            }

            return multipartFiles;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
