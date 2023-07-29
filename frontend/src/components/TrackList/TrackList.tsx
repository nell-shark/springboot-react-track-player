import ListGroup from 'react-bootstrap/ListGroup';

import { TrackItem } from '@components/TrackList/TrackItem';

import { useAppSelector } from '@hooks/redux';

import { TrackInfo } from '@typings/track';

interface TrackListProps {
  tracks: TrackInfo[];
}

export function TrackList({ tracks }: TrackListProps) {
  const state = useAppSelector(state => state.trackPlayer);

  if (state.isLoadingPage) return <p>Loading...</p>;
  if (state.error) return <p>{state.error}</p>;

  return (
    <ListGroup>
      {tracks.map(track => (
        <TrackItem key={track.id} trackInfo={track} />
      ))}
    </ListGroup>
  );
}
