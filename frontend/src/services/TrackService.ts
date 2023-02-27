import { Track } from '@interfaces/track';
import { axiosInstance } from '@/services/axios-instance';

export class TrackService {
  public getAllTracks() {
    return axiosInstance.get<Track[]>('/api/v1/tracks');
  }

  public searchTracks(search: string) {
    return axiosInstance.get<Track[]>('/api/v1/tracks', {
      params: { search },
    });
  }
}

export const trackService = new TrackService();
