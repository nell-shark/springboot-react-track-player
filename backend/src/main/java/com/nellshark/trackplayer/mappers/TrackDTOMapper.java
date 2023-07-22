package com.nellshark.trackplayer.mappers;

import com.nellshark.trackplayer.dto.TrackDTO;
import com.nellshark.trackplayer.models.Track;
import org.springframework.stereotype.Component;

@Component
public class TrackDTOMapper {
    public TrackDTO toDTO(Track track) {
        return new TrackDTO(
                track.getId(),
                track.getName()
        );
    }
}
