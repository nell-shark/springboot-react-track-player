import { applyMiddleware, combineReducers, createStore } from 'redux';
import thunk from 'redux-thunk';

import { trackPlayerReducer } from './trackPlayerReducer';

const rootReducer = combineReducers({
  trackPlayer: trackPlayerReducer
});

export type RootState = ReturnType<typeof rootReducer>;

export const store = createStore(rootReducer, applyMiddleware(thunk));