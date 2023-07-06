import { Page } from '@typings/page';

import { useAppSelector } from '@hooks/redux';
import { useTitle } from '@hooks/useTitle';

import { AddTrack } from '@pages/Tracks/AddTrack';
import { TrackList } from '@pages/Tracks/TrackList';

export interface TracksProps extends Page {}

export function Tracks({ title }: TracksProps) {
  useTitle(title);

  const tracks = useAppSelector(state => state.trackPlayer.trackList);

  return (
    <>
      <TrackList isLoading={false} tracks={tracks} error={''} />
      <div className='mt-4 d-flex justify-content-center gap-3'>
        {/*<ShowMore isFetching={isFetching} fetchNextPage={fetchNextPage} hasNextPage={hasNextPage} />*/}
        <AddTrack />
      </div>
    </>
  );
}