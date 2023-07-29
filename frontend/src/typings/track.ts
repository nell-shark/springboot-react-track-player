export interface TrackInfo {
  id: string;
  name: string;
}

export interface Track extends TrackInfo {
  seconds: number;
  bytes: string;
}

export interface TrackListPage {
  page: number;
  hasNext: boolean;
  tracks: TrackInfo[];
}
