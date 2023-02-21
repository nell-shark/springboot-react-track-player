import { useEffect, useState } from 'react';

import { AxiosError } from 'axios';
import { Track } from '@interfaces/track';
import { TrackService } from '@services/TrackService';

const trackService: TrackService = TrackService.getInstance();

export function useTracks() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [tracks, setTracks] = useState<Track[]>([]);

  async function fetchTracks() {
    setLoading(() => true);
    try {
      const response = await trackService.getAllTracks();
      setTracks(response.data);
      setLoading(false);
    } catch (err) {
      const error = err as AxiosError | Error;
      setLoading(false);
      setError(error.message);
    }
  }

  useEffect(() => {
    fetchTracks();
  }, []);

  return { loading, error, tracks };
}
