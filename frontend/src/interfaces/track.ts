export type TrackInfo = {
    id: string;
    name: string;
    seconds: number;
};

export type TracksPage = {
    currentPage: number
    hasNext: boolean,
    tracks: TrackInfo[]
};

export type Track = {
    id: string;
}