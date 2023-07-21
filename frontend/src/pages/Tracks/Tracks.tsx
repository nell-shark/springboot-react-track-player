import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';

import { TrackList } from '@components/TrackList';

import { useAppDispatch, useAppSelector } from '@hooks/redux';
import { useTitle } from '@hooks/useTitle';

import { AddTrack } from '@pages/Tracks/AddTrack';
import { ShowMore } from '@pages/Tracks/ShowMore';

import { getTracListPage } from '@store/slices';

import { Page } from '@typings/page';


export interface TracksProps extends Page {}

export function Tracks({ title }: TracksProps) {
  useTitle(title);

  const dispatch = useAppDispatch();
  const state = useAppSelector(state => state.trackPlayer);
  const [searchParams] = useSearchParams();

  useEffect(() => {
    dispatch(getTracListPage({ page: state.page, filter: searchParams.get('filter') || undefined }));
  }, [state.page]);

  return (
    <>
      <TrackList tracks={state.trackList} />
      <div className='mt-4 d-flex justify-content-center gap-3'>
        <ShowMore />
        <AddTrack />
      </div>
    </>
  );
}
