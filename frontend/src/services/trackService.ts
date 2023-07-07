import { axiosInstance } from '@services/axiosInstance';

import { Track, TrackListPage } from '@typings/track';

class TrackService {
  public getTrackListPage(page: number = 1, filter?: string) {
    return axiosInstance.get<TrackListPage>('/api/v1/tracks', {
      params: { page, filter }
    });
  }

  public getTrackById(id: string) {
    return axiosInstance.get<Track>(`/api/v1/tracks/${id}`);
  }

  public uploadTrack(name: string, track: File) {
    return axiosInstance.post(
      '/api/v1/tracks',
      { name, track },
      {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
    );
  }
}

export const trackService = new TrackService();