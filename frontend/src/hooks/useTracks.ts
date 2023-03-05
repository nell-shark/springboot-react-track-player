import {useEffect, useState} from 'react';

import {AxiosError} from 'axios';
import {Track} from '@interfaces/track';
import {trackService} from '@services/TrackService';
import {useSearchParams} from 'react-router-dom';

let page: number = 0;

export function useTracks() {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string>('');
  const [searchParams] = useSearchParams();
  const [tracks, setTracks] = useState<Track[]>([]);
  const [hideShowMore, setHideShowMore] = useState<boolean>(false);

  async function fetchTracks() {
    setLoading(() => true);
    setError(() => '');
    try {
      const filter = searchParams.get('filter') || undefined;
      const {data: response} = await trackService.getTracks(page, filter);

      if (response.length <= 0) setHideShowMore(true);
      else setHideShowMore(false);

      setTracks((prevTracks) => prevTracks.concat(response));
    } catch (err) {
      const e = err as AxiosError | Error;
      setError(e.message);
    }
    setLoading(false);
  }

  function addTrack(track: Track) {
    setTracks(() => tracks.concat(track));
  }

  function showMore() {
    page += 1;
    fetchTracks();
  }

  useEffect(() => {
    page = 0;
    setTracks(() => []);
    fetchTracks();
  }, [searchParams])

  return {loading, error, tracks, addTrack, showMore, hideShowMore};
}
