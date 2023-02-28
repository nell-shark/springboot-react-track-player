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

  public async addTrack(name: string, file: File) {
    const audioContext = new window.AudioContext();
    const fileBuffer = await file.arrayBuffer();
    const audioBuffer = await audioContext.decodeAudioData(fileBuffer);
    const duration: number = Math.round(audioBuffer.duration);

    return axiosInstance.post(
      '/api/v1/tracks',
      {
        name,
        file,
        duration,
      },
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );
  }
}

export const trackService = new TrackService();
