import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { TrackPlayerState } from '@typings/track';

const initialState: TrackPlayerState = {
  hasPrevious: false,
  trackId: undefined,
  hasNext: false,
  status: 'disabled',
  tracks: []
};

const trackPlayer = createSlice({
  name: 'trackPlayer',
  initialState,
  reducers: {
    playTrack: (state, action: PayloadAction<string>) => {
      state.trackId = action.payload;
      state.status = 'playing';
    }
  }
});

export const trackPlayerReducer = trackPlayer.reducer;
export const { playTrack } = trackPlayer.actions;