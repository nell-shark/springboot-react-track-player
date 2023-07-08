import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';

import { trackService } from '@services/trackService';

import { Track } from '@typings/track';

interface TrackPlayerState {
  disabled: boolean;
  page: number;
  isLoadingPage: boolean;
  hasNextPage: boolean;
  trackList: Track[];
  hasPrevTrack: boolean;
  hasNextTrack: boolean;
  track?: Track;
  playing: boolean;
  error?: string;
}

const initialState: TrackPlayerState = {
  disabled: true,
  page: 1,
  isLoadingPage: true,
  hasNextPage: false,
  trackList: [],
  hasPrevTrack: false,
  hasNextTrack: false,
  playing: false
};

export const getListTrackPage = createAsyncThunk('trackPlayer/getListTrackPage', async (page: number, thunkAPI) => {
  try {
    const response = await trackService.getTrackListPage(page);
    return response.data;
  } catch (error) {
    console.error(error);
    return thunkAPI.rejectWithValue(error);
  }
});

const trackPlayer = createSlice({
  name: 'trackPlayer',
  initialState,
  reducers: {
    playTrack(state, action: PayloadAction<Track>) {
      state.playing = true;
      state.disabled = false;
      state.track = action.payload;
    },
    pauseTrack(state) {
      state.playing = false;
    },
    prevTrack(state) {
      let index = state.trackList.indexOf(state.track!);
      state.hasPrevTrack = index > 1;
      state.track = state.trackList.at(index - Number(state.hasPrevTrack));
      state.playing = true;
    },
    nextTrack(state) {
      let index = state.trackList.indexOf(state.track!);
      state.hasNextTrack = state.trackList.length > index - 1;
      state.track = state.trackList.at(index + Number(state.hasNextTrack));
      state.playing = true;
    }
  },
  extraReducers: builder => {
    builder
      .addCase(getListTrackPage.pending, state => {
        state.isLoadingPage = true;
      })
      .addCase(getListTrackPage.fulfilled, (state, action) => {
        state.isLoadingPage = false;
        state.trackList = action.payload.tracks;
        state.page = action.payload.page;
        state.hasNextPage = action.payload.hasNext;
      })
      .addCase(getListTrackPage.rejected, (state, action) => {
        state.isLoadingPage = false;
        state.error = action.error.message;
      });
  }
});

export const trackPlayerReducer = trackPlayer.reducer;
export const { playTrack, pauseTrack, prevTrack, nextTrack } = trackPlayer.actions;
