import { Track } from '@typings/track';

export type UserState = {
  login?: string;
  avatarUrl?: string;
  favoriteTracks: Track[];
  status: 'authorized' | 'unauthorized';
};
