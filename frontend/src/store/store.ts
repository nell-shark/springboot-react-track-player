import { configureStore } from '@reduxjs/toolkit';

import { trackPlayerReducer } from './reducers';

export const store = configureStore({
  reducer: {
    trackPlayer: trackPlayerReducer
  }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;