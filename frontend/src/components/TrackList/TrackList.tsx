import ListGroup from 'react-bootstrap/ListGroup';

import { TrackItem } from '@components/TrackList/TrackItem';

import { useAppSelector } from '@hooks/redux';

import { Track } from '@typings/track';

interface TrackListProps {
  tracks: Track[];
}

export function TrackList({ tracks }: TrackListProps) {
  const state = useAppSelector(state => state.trackPlayer);

  if (state.isLoadingPage) return <p>Loading...</p>;
  if (state.error) return <p>{state.error}</p>;

  return (
    <ListGroup>
      {tracks.map(track => (
        <TrackItem key={track.id} track={track} />
      ))}
    </ListGroup>
  );
}
