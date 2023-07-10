import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { AxiosError } from 'axios';

import { trackService } from '@services/trackService';

import { Track } from '@typings/track';

interface TrackPlayerState {
  page: number;
  isLoadingPage: boolean;
  hasNextPage: boolean;
  trackList: Track[];
  hasPrevTrack: boolean;
  hasNextTrack: boolean;
  playing: boolean;
  track?: Track;
  error?: string;
}

const initialState: TrackPlayerState = {
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
    const { data } = await trackService.getTrackListPage(page);
    return data;
  } catch (error) {
    return thunkAPI.rejectWithValue(error);
  }
});

const trackPlayer = createSlice({
  name: 'trackPlayer',
  initialState,
  reducers: {
    togglePlayTrack(state, action: PayloadAction<Track>) {
      state.playing = !state.playing;
      state.track = action.payload;
      const index = state.trackList.findIndex(t => t.id === action.payload.id);
      state.hasPrevTrack = index > 0;
      state.hasNextTrack = state.trackList.length - 1 > index;
    },
    playPrevTrack(state) {
      const index = state.trackList.indexOf(state.track!);
      state.hasPrevTrack = index > 0;
      state.track = state.hasPrevTrack ? state.trackList[index - 1] : undefined;
      state.playing = true;
    },
    playNextTrack(state) {
      const index = state.trackList.findIndex(t => state.track!.id === t.id);
      state.hasNextTrack = state.trackList.length - 1 > index;
      state.track = state.hasNextTrack ? state.trackList[index + 1] : undefined;
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
        state.error = action.payload instanceof AxiosError ? action.payload.message : 'Unknown error occurred';
      });
  }
});

export const trackPlayerReducer = trackPlayer.reducer;
export const { togglePlayTrack, playPrevTrack, playNextTrack } = trackPlayer.actions;
