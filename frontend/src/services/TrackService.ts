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

  public addTrack(name: string, file: File) {
    const formData = new FormData();
    formData.append('name', name);
    formData.append('file', file);

    return axiosInstance.post('/api/v1/tracks', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  }
}

export const trackService = new TrackService();
