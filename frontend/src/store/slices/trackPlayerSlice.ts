import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Track } from '@typings/track';

interface TrackPlayerState {
  hasPrevious: boolean;
  hasNext: boolean;
  trackList: Track[];
  track?: Track;
  status: 'disabled' | 'play' | 'pause';
}

const initialState: TrackPlayerState = {
  hasPrevious: false,
  track: undefined,
  hasNext: false,
  status: 'disabled',
  trackList: []
};

const trackPlayer = createSlice({
  name: 'trackPlayer',
  initialState,
  reducers: {
    playTrack(state, action: PayloadAction<Track>) {
      state.status = 'play';
      state.track = action.payload;
    },
    pauseTrack(state) {
      state.status = 'pause';
    },
    previousTrack(state) {
      let index = state.trackList.indexOf(state.track!);
      state.hasPrevious = index > 1;
      state.track = state.trackList.at(index - Number(state.hasPrevious));
      state.status = 'play';
    },
    nextTrack(state) {
      let index = state.trackList.indexOf(state.track!);
      state.hasNext = state.trackList.length > index - 1;
      state.track = state.trackList.at(index + Number(state.hasNext));
      state.status = 'play';
    }
  }
});

export const trackPlayerReducer = trackPlayer.reducer;
export const { playTrack, pauseTrack, previousTrack, nextTrack } = trackPlayer.actions;