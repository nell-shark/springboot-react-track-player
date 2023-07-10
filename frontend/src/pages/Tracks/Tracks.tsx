import { useTitle } from '@hooks/useTitle';

import { AddTrack } from '@pages/Tracks/AddTrack';
import { TrackList } from '@pages/Tracks/TrackList';

import { Page } from '@typings/page';

export interface TracksProps extends Page {}

export function Tracks({ title }: TracksProps) {
  useTitle(title);

  return (
    <>
      <TrackList />
      <div className='mt-4 d-flex justify-content-center gap-3'>
        {/*<ShowMore isFetching={isFetching} fetchNextPage={fetchNextPage} hasNextPage={hasNextPage} />*/}
        <AddTrack />
      </div>
    </>
  );
}
