import { useEffect, useState } from 'react';

import { AxiosError } from 'axios';
import { Track } from '@interfaces/track';
import { trackService } from '@/services/TrackService';
import { useSearchParams } from 'react-router-dom';

export function useTracks() {
  const [loading, setLoading] = useState(true);
  const [tracks, setTracks] = useState<Track[]>([]);
  const [error, setError] = useState<string>('');
  const [searchParams] = useSearchParams();

  async function fetchTracks() {
    setLoading(() => true);
    setError(() => '');
    try {
      const search = searchParams.get('search');
      const response = search
        ? await trackService.searchTracks(search)
        : await trackService.getAllTracks();

      setTracks(response.data);
    } catch (err) {
      const e = err as AxiosError | Error;
      setError(e.message);
    }
    setLoading(false);
  }

  useEffect(() => {
    fetchTracks();
  }, [searchParams]);

  return { loading, error, tracks };
}
