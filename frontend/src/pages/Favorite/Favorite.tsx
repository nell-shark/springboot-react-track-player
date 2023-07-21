import { TrackList } from '@components/TrackList';

import { useAppSelector } from '@hooks/redux';
import { useTitle } from '@hooks/useTitle';

import { ShowMore } from '@pages/Tracks/ShowMore';

import { Page } from '@typings/page';

interface FavoriteProps extends Page {}

export function Favorite({ title }: FavoriteProps) {
  useTitle(title);
  const state = useAppSelector(state => state.user);

  return (
    <>
      <TrackList tracks={state.user?.favoriteTracks || []} />
      <div className='mt-4 d-flex justify-content-center gap-3'>
        <ShowMore />
      </div>
    </>
  );
}
