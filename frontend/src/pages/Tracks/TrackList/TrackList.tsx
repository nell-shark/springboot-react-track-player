import { useEffect } from 'react';
import ListGroup from 'react-bootstrap/ListGroup';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { TrackItem } from '@pages/Tracks/TrackList/TrackItem';

import { getListTrackPage } from '@store/slices';


export function TrackList() {
  const dispatch = useAppDispatch();
  const state = useAppSelector(state => state.trackPlayer);

  useEffect(() => {
    dispatch(getListTrackPage(state.page));
  }, [dispatch, state.page]);

  if (state.isLoadingPage) return <p>Loading...</p>;
  if (state.error) return <p>{state.error}</p>;

  return (
    <ListGroup>
      {state.trackList.map(track => (
        <TrackItem key={track.id} track={track} favorite={false} />
      ))}
    </ListGroup>
  );
}
