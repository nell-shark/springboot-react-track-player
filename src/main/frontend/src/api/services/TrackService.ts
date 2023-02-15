import { Track } from '@interfaces/track';
import { api } from '../api';

export class TrackService {
  private static instance: TrackService;

  private constructor() {}

  public static getInstance(): TrackService {
    if (!TrackService.instance) {
      TrackService.instance = new TrackService();
    }

    return TrackService.instance;
  }

  public getAllTracks() {
    return api.get<Track[]>('/api/v1/tracks');
  }
}
