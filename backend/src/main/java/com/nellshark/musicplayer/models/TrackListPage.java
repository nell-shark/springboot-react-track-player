package com.nellshark.musicplayer.models;

import com.nellshark.musicplayer.dto.TrackDTO;

import java.util.List;

public record TrackListPage(int page,
                            boolean hasNext,
                            List<TrackDTO> tracks) {
}
