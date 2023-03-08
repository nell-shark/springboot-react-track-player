import {Track, TracksPage} from '@interfaces/track';
import {axiosInstance} from '@services/axios-instance';

export class TrackService {
  public getTracks(page: number = 1, filter?: string) {
    return axiosInstance.get<TracksPage>('/api/v1/tracks', {
      params: {page, filter}
    });
  }

  public getTrackById(id: string) {
    return axiosInstance.get<Track>(`/api/v1/tracks/${id}`);
  }

  public async postTrack(name: string, file: File) {
    const audioContext = new window.AudioContext();
    const fileBuffer = await file.arrayBuffer();
    const audioBuffer = await audioContext.decodeAudioData(fileBuffer);
    const seconds: number = Math.round(audioBuffer.duration);

    return axiosInstance.post<string>(
      '/api/v1/tracks',
      {name, file, seconds},
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );
  }
}

export const trackService = new TrackService();
