package com.nellshark.musicplayer.models;

import java.util.List;

public record TrackListPage(int page,
                            boolean hasNext,
                            List<Track> tracks) {
}
