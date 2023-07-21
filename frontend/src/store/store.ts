import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

import { trackPlayerReducer } from '@store/slices';
import { userReducer } from '@store/slices/userSlice';

const persistConfig = {
  key: 'root',
  blacklist: ['trackPlayer'],
  storage
};

const rootReducer = combineReducers({
  trackPlayer: trackPlayerReducer,
  user: userReducer
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: getDefaultMiddleware =>
    getDefaultMiddleware({
      serializableCheck: false
    })
});

export const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
