import { Track } from '@typings/track';

export interface TrackPlayerState {
  hasPrevious: boolean;
  track?: Track;
  hasNext: boolean;
}

export enum PlayerTrackActionTypes {
  PLAY_TRACK = 'PLAY_TRACK',
  STOP_TRACK = 'STOP_TRACK',
  PLAY_NEXT_TRACK = 'PLAY_NEXT_TRACK',
  PLAY_PREVIOUS_TRACK = 'PLAY_PREVIOUS_TRACK'
}

export type TrackPlayerAction = PlayTrackAction | StopTrackAction | PlayNextTrackAction | PlayPreviousTrackAction;

interface PlayTrackAction {
  type: PlayerTrackActionTypes.PLAY_TRACK;
  payload: Track;
}

interface StopTrackAction {
  type: PlayerTrackActionTypes.STOP_TRACK;
  payload: Track;
}

interface PlayNextTrackAction {
  type: PlayerTrackActionTypes.PLAY_NEXT_TRACK;
  payload: Track;
}

interface PlayPreviousTrackAction {
  type: PlayerTrackActionTypes.PLAY_PREVIOUS_TRACK;
  payload: Track;
}