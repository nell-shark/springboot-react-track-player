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
    addFavoriteTrack(state, action: PayloadAction<Track>) {
      if (state.user) state.user!.favoriteTracks.push(action.payload);
    },
    removeUser(state) {
      state.user = null;
    }
  }
});

export const userReducer = userSlice.reducer;
export const { setUser, addFavoriteTrack, removeUser } = userSlice.actions;
