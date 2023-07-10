import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { Track } from '@typings/track';

interface UserState {
  login?: string;
  avatarUrl?: string;
  favoriteTracks: Track[];
  authorized: boolean;
}

const initialState: UserState = {
  favoriteTracks: [],
  authorized: false
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUser(state, action: PayloadAction<{ login: string; avatarUrl: string }>) {
      state.login = action.payload.login;
      state.avatarUrl = action.payload.avatarUrl;
      state.authorized = true;
    },
    addFavoriteTrack(state, action: PayloadAction<Track>) {
      state.favoriteTracks.push(action.payload);
    },
    removeUser(state) {
      state.login = undefined;
      state.avatarUrl = undefined;
      state.favoriteTracks = [];
      state.authorized = false;
    }
  }
});

export const userReducer = userSlice.reducer;
export const { setUser, addFavoriteTrack, removeUser } = userSlice.actions;
