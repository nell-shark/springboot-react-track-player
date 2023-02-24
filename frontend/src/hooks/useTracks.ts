import { useEffect, useState } from 'react';

import { AxiosError } from 'axios';
import { Track } from '@interfaces/track';
import { trackService } from '@/services/TrackService';

export function useTracks() {
  const [loading, setLoading] = useState(true);
  const [tracks, setTracks] = useState<Track[]>([]);
  const [error, setError] = useState('');

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
