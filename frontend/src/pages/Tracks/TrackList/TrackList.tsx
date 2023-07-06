import { Track } from '@typings/track';

import ListGroup from 'react-bootstrap/ListGroup';

import { TrackItem } from '@pages/Tracks/TrackList/TrackItem';

interface TrackListProps {
  isLoading: boolean;
  tracks: Track[];
  error?: string;
}

export function TrackList({ isLoading, tracks, error }: TrackListProps) {
  if (isLoading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <ListGroup>
      {tracks.map(track => (
        <TrackItem key={track.id} track={track} favorite={false} />
      ))}
    </ListGroup>
  );
}