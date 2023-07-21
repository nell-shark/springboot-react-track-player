import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { AxiosError } from 'axios';

import { trackService } from '@services/trackService';

import { Track, TrackListPage } from '@typings/track';

interface TrackPlayerState {
  page: number;
  isLoadingPage: boolean;
  hasNextPage: boolean;
  trackList: Track[];
  hasNextTrack: boolean;
  playing: boolean;
  track: Track | null;
  error: string | null;
}

const initialState: TrackPlayerState = {
  page: 1,
  isLoadingPage: true,
  hasNextPage: false,
  trackList: [],
  hasNextTrack: false,
  playing: false,
  track: null,
  error: null
};

export const getTracListPage = createAsyncThunk(
  'trackPlayer/getListTrackPage',
  async ({ page, filter }: { page: number; filter?: string }, thunkAPI) => {
    try {
      const { data } = await trackService.getTrackListPage(page, filter);
      return data;
    } catch (error) {
      return thunkAPI.rejectWithValue(error);
    }
  }
);

const trackPlayer = createSlice({
  name: 'trackPlayer',
  initialState,
  reducers: {
    togglePlayTrack(state, action: PayloadAction<Track>) {
      if (!state.track) state.playing = true;
      else state.playing = state.track.id === action.payload.id ? !state.playing : true;
      state.track = action.payload;
      const index = state.trackList.findIndex(t => t.id === action.payload.id);
      state.hasNextTrack = state.trackList.length - 1 > index;
    },
    playNextTrack(state) {
      const index = state.trackList.findIndex(t => state.track!.id === t.id);
      state.hasNextTrack = state.trackList.length - 1 > index;
      state.track = state.hasNextTrack ? state.trackList[index + 1]! : null;
      state.playing = true;
    }
  },
  extraReducers: builder => {
    builder
      .addCase(getTracListPage.pending, state => {
        state.isLoadingPage = true;
      })
      .addCase(getTracListPage.fulfilled, (state, action: PayloadAction<TrackListPage>) => {
        state.isLoadingPage = false;
        state.trackList.push(...action.payload.tracks);
        state.page = action.payload.page;
        state.hasNextPage = action.payload.hasNext;
      })
      .addCase(getTracListPage.rejected, (state, action) => {
        state.isLoadingPage = false;
        state.error = action.payload instanceof AxiosError ? action.payload.message : 'Unknown error occurred';
      });
  }
});

export const trackPlayerReducer = trackPlayer.reducer;
export const { togglePlayTrack, playNextTrack } = trackPlayer.actions;
