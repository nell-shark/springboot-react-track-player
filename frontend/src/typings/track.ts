export type Track = {
  id: string;
  name: string;
  seconds: number;
};

export type TracksPage = {
  currentPage: number;
  hasNext: boolean;
  tracks: Track[];
};

export type TrackPlayerState = {
  hasPrevious: boolean;
  hasNext: boolean;
  tracks: Track[];
  trackId?: string;
  status: 'disabled' | 'playing' | 'pause';
};
