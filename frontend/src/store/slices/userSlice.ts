import { createSlice, PayloadAction } from '@reduxjs/toolkit';

import { Track } from '@typings/track';
import { User } from '@typings/user';

interface UserState {
  user: User | null;
}

const initialState: UserState = {
  user: null
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUser(state, action: PayloadAction<User>) {
      state.user = action.payload;
    },
    toggleFavoriteTrack(state, action: PayloadAction<Track>) {
      if (!state.user) return;

      if (!state.user.favoriteTracks.find(t => t.id === action.payload.id))
        state.user.favoriteTracks.push(action.payload);
      else state.user.favoriteTracks = state.user.favoriteTracks.filter(t => t.id !== action.payload.id);
    },
    removeUser(state) {
      state.user = null;
    }
  }
});

export const userReducer = userSlice.reducer;
export const { setUser, toggleFavoriteTrack, removeUser } = userSlice.actions;
