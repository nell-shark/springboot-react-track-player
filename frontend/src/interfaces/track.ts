export type Track = {
  id: string;
  name: string;
  seconds: number;
  author: string;
};

export type TracksPage = {
  currentPage: number
  hasNext: boolean,
  tracks: Track[]
};