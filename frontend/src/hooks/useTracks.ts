import {TracksPage} from '@interfaces/track';
import {trackService} from '@services/TrackService';
import {useSearchParams} from 'react-router-dom';
import {useInfiniteQuery} from "@tanstack/react-query";
import {TRACKS} from "@data/query-keys";
import {useEffect, useState} from "react";


export function useTracks() {
  const [searchParams] = useSearchParams();

  const {data, isLoading, isFetching, error, hasNextPage, fetchNextPage, refetch} = useInfiniteQuery<TracksPage, Error>({
    queryKey: [TRACKS],
    getNextPageParam: (lastPage) => lastPage.hasNext ? lastPage.currentPage + 1 : undefined,
    queryFn: ({pageParam = 1}) => fetchTrack(pageParam)
  })

  async function fetchTrack(pageParam: number) {
    const filter = searchParams.get('filter') || undefined;
    const {data} = await trackService.getTracks(pageParam, filter);
    return data;
  }

  useEffect(() => {
    refetch();
  }, [searchParams])

  return {isLoading, isFetching, error, data, fetchNextPage, hasNextPage};
}
