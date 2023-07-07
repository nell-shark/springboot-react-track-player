export interface Track {
  id: string;
  name: string;
  bytes?: string;
}

export interface TrackListPage {
  page: number;
  hasNext: boolean;
  tracks: Track[];
}
