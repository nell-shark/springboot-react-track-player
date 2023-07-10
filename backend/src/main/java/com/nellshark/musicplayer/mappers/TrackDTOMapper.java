package com.nellshark.musicplayer.mappers;

import com.nellshark.musicplayer.dto.TrackDTO;
import com.nellshark.musicplayer.models.Track;
import org.springframework.stereotype.Component;

@Component
public class TrackDTOMapper {
    public TrackDTO toDTO(Track track) {
        return new TrackDTO(
                track.getId(),
                track.getName(),
                track.getBytes()
        );
    }
}
