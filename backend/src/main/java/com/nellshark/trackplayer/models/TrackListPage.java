package com.nellshark.trackplayer.models;

import com.nellshark.trackplayer.dto.TrackDTO;

import java.util.List;

public record TrackListPage(int page,
                            boolean hasNext,
                            List<TrackDTO> tracks) {
}
