import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { TrackPlayer } from '@typings/track';

const initialState: TrackPlayer = {
  hasPrevious: false,
  track: undefined,
  hasNext: false
};

const trackPlayerSlice = createSlice({
  name: 'trackPlayer',
  initialState,
  reducers: {
    playTrack: (state, action: PayloadAction<TrackPlayer>) => {
      state.track = action.payload.track;
    },
    print: () => {
      console.log('print');
    }
  }
});

export const trackPlayerReducer = trackPlayerSlice.reducer;
export const { playTrack, print } = trackPlayerSlice.actions;