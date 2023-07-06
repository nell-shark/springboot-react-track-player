export interface Track {
  id: string;
  name: string;
  seconds: number;
  bytes?: Blob;
}

export interface TracksPage {
  currentPage: number;
  hasNext: boolean;
  tracks: Track[];
}
