export type Track = {
  id: string;
  name: string;
  seconds: number;
};

export type TracksPage = {
  currentPage: number
  hasNext: boolean,
  tracks: Track[]
};