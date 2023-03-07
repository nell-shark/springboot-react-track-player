import {useEffect, useState} from 'react';
import {Track} from '@interfaces/track';
import {trackService} from '@services/TrackService';
import {useSearchParams} from 'react-router-dom';
import {useQuery} from "@tanstack/react-query";
import {TRACKS} from "@data/query-keys";


export function useTracks() {
  const [tracks, setTracks] = useState<Track[]>([]);
  const [page, setPage] = useState<number>(0);
  const [hasMore, setHasMore] = useState<boolean>(true);

  const [searchParams] = useSearchParams();

  const {isFetching, error, refetch} = useQuery<Track[], Error>({
    queryKey: [TRACKS, page],
    queryFn: fetchTracks,
    onSuccess: (data) => updateTrackList(data),
    keepPreviousData: true
  });

  async function fetchTracks() {
    const filter = searchParams.get('filter') || undefined;
    const {data: tracks} = await trackService.getTracks(page, filter);
    setHasMore(() => (tracks.length >= 10));
    return tracks;
  }

  function updateTrackList(data: Track[]) {
    for (const newTrack of data) {
      if (!tracks.some(oldTrack => oldTrack.id === newTrack.id))
        setTracks((prev) => prev.concat(newTrack));
    }
  }

  function showMore() {
    setPage((prev) => prev + 1)
  }

  useEffect(() => {
    setTracks(() => []);
    refetch();
  }, [searchParams])

  return {isFetching, error, tracks, showMore, hasMore};
}
