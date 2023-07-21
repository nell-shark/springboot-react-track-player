import { TrackList } from '@components/TrackList';

import { useAppSelector } from '@hooks/redux';
import { useTitle } from '@hooks/useTitle';

import { ShowMore } from '@pages/Tracks/ShowMore';

import { Page } from '@typings/page';

interface FavoriteTracksProps extends Page {}

export function FavoriteTracks({ title }: FavoriteTracksProps) {
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
