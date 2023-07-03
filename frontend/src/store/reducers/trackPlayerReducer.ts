import { PlayerTrackActionTypes, TrackPlayerAction, TrackPlayerState } from '@typings/trackPlayer';

const initialState: TrackPlayerState = {
  hasPrevious: false,
  track: undefined,
  hasNext: false
};

export const trackPlayerReducer = (state = initialState, action: TrackPlayerAction): TrackPlayerState => {
  switch (action.type) {
    case PlayerTrackActionTypes.PLAY_TRACK:
      return { ...state };
    case PlayerTrackActionTypes.STOP_TRACK:
      return { ...state };
    case PlayerTrackActionTypes.PLAY_NEXT_TRACK:
      return { ...state };
    case PlayerTrackActionTypes.PLAY_PREVIOUS_TRACK:
      return { ...state };
    default:
      return state;
  }
};