import { useEffect } from 'react';

import { useAppDispatch, useAppSelector } from '@hooks/redux';
import { useTitle } from '@hooks/useTitle';

import { AddTrack } from '@pages/Tracks/AddTrack';
import { TrackList } from '@pages/Tracks/TrackList';

import { getListTrackPage } from '@store/slices';

import { Page } from '@typings/page';


export interface TracksProps extends Page {}

export function Tracks({ title }: TracksProps) {
  useTitle(title);

  const dispatch = useAppDispatch();
  const state = useAppSelector(state => state.trackPlayer);

  useEffect(() => {
    dispatch(getListTrackPage(1));
  }, [dispatch]);

  return (
    <>
      <TrackList isLoading={state.isLoadingPage} tracks={state.trackList} error={state.error} />
      <div className='mt-4 d-flex justify-content-center gap-3'>
        {/*<ShowMore isFetching={isFetching} fetchNextPage={fetchNextPage} hasNextPage={hasNextPage} />*/}
        <AddTrack />
      </div>
    </>
  );
}