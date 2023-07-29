import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { AxiosError } from 'axios';

import { trackService } from '@services/trackService';

import { TrackInfo, TrackListPage } from '@typings/track';

interface TrackPlayerState {
  page: number;
  isLoadingPage: boolean;
  trackListPage: TrackListPage | null;
  isLoadingTrack: boolean;
  isPlaying: boolean;
  track: TrackInfo | null;
  error: string | null;
}

const initialState: TrackPlayerState = {
  page: 1,
  isLoadingPage: true,
  trackListPage: { page: 0, hasNext: false, tracks: [] },
  isLoadingTrack: false,
  isPlaying: false,
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
    playTrack(state, action: PayloadAction<TrackInfo>) {
      if (!state.track) state.isPlaying = true;
      else state.isPlaying = state.track.id === action.payload.id ? !state.isPlaying : true;
    },
    setPage(state, action: PayloadAction<number>) {
      state.page = action.payload;
    }
  },
  extraReducers: builder => {
    builder
      .addCase(getTracListPage.pending, state => {
        state.isLoadingPage = true;
      })
      .addCase(getTracListPage.fulfilled, (state, action: PayloadAction<TrackListPage>) => {
        state.isLoadingPage = false;

        if (state.trackListPage) {
          const existingTrackIds = new Set(state.trackListPage.tracks.map(t => t.id));

          action.payload.tracks
            .filter(t => !existingTrackIds.has(t.id))
            .forEach(t => {
              state.trackListPage!.tracks.push(t);
              existingTrackIds.add(t.id);
            });

          state.page = action.payload.page;
          state.trackListPage.hasNext = action.payload.hasNext;
        } else {
          state.trackListPage = action.payload;
        }
      })
      .addCase(getTracListPage.rejected, (state, action) => {
        state.isLoadingPage = false;
        state.error = action.payload instanceof AxiosError ? action.payload.message : 'Unknown error occurred';
      });
  }
});

export const trackPlayerReducer = trackPlayer.reducer;
export const { playTrack, setPage } = trackPlayer.actions;
