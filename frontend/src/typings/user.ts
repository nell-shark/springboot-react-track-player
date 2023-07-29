import { TrackInfo } from '@typings/track';

export interface User {
  login: string;
  avatarUrl: string;
  favoriteTracks: TrackInfo[];
}
