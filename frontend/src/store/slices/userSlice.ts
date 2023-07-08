import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { Track } from '@typings/track';
import { UserState } from '@typings/userState';

const initialState: UserState = {
  login: undefined,
  avatarUrl: undefined,
  favoriteTracks: [],
  status: 'unauthorized'
};

const user = createSlice({
  name: 'user',
  initialState,
  reducers: {
    addFavoriteTrack: (state, action: PayloadAction<Track>) => {
      state.favoriteTracks.push(action.payload);
    }
  }
});

export const userReducer = user.reducer;
export const { addFavoriteTrack } = user.actions;
