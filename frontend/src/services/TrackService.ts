import {Track} from '@interfaces/track';
import {axiosInstance} from '@services/axios-instance';

export class TrackService {
  public getAllTracks(page: number, filter?: string) {
    return axiosInstance.get<Track[]>('/api/v1/tracks', {
      params: {page, filter}
    });
  }

  public getTrackById(id: string) {
    return axiosInstance.get<Track>(`/api/v1/tracks/${id}`);
  }

  public async addTrack(name: string, file: File) {
    const audioContext = new window.AudioContext();
    const fileBuffer = await file.arrayBuffer();
    const audioBuffer = await audioContext.decodeAudioData(fileBuffer);
    const duration: number = Math.round(audioBuffer.duration);

    return axiosInstance.post<string>(
      '/api/v1/tracks',
      {name, file, duration},
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );
  }
}

export const trackService = new TrackService();
