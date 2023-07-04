import { Page } from '@typings/page';

import { useAppDispatch } from '@hooks/redux';
import { useTitle } from '@hooks/useTitle';
import { useTracks } from '@hooks/useTracks';

import { AddTrack } from '@pages/Tracks/AddTrack';
import { ShowMore } from '@pages/Tracks/ShowMore';
import { TrackList } from '@pages/Tracks/TrackList';

import { print } from '@store/reducers';


export interface TracksProps extends Page {}

export function Tracks({ title }: TracksProps) {
  const { data, isLoading, isFetching, error, fetchNextPage, hasNextPage } = useTracks();
  useTitle(title);

  const dispatch = useAppDispatch();
  dispatch(print());

  return (
    <>
      <TrackList
        isLoading={isLoading}
        tracks={data?.pages.flatMap(value => value.tracks) || []}
        error={error || undefined}
      />
      <div className='mt-4 d-flex justify-content-center gap-3'>
        <ShowMore isFetching={isFetching} fetchNextPage={fetchNextPage} hasNextPage={hasNextPage} />
        <AddTrack />
      </div>
    </>
  );
}