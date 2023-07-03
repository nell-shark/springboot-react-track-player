import { PlayerTrackActionTypes, TrackPlayerAction } from '@typings/trackPlayer';
import { Dispatch } from 'redux';

import { trackService } from '@services/trackService';

export function playTrack() {
  return async (dispatch: Dispatch<TrackPlayerAction>) => {
    let { data } = await trackService.getTrackById('1');
    dispatch({ type: PlayerTrackActionTypes.PLAY_TRACK, payload: data });
  };
}