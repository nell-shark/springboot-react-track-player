package com.nellshark.musicplayer.mappers;

import com.nellshark.musicplayer.dto.TrackDTO;
import com.nellshark.musicplayer.models.Track;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TrackDTOMapper implements Function<Track, TrackDTO> {
    @Override
    public TrackDTO apply(Track track) {
        return new TrackDTO(
                track.getId(),
                track.getName(),
                track.getBytes()
        );
    }
}
