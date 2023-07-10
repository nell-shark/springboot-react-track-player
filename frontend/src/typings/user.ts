import { Track } from '@typings/track';

export interface User {
  login: string;
  avatarUrl: string;
  favoriteTracks: Track[];
}
