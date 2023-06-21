import ListGroup from 'react-bootstrap/ListGroup';

import { TrackInfo } from '@interfaces/track';

import { TrackItem } from '@pages/Tracks/TrackList/TrackItem';

interface TrackListProps {
  isLoading: boolean;
  tracks: TrackInfo[];
  error?: Error;
}

export function TrackList({ isLoading, tracks, error }: TrackListProps) {
  if (isLoading) return <p>Loading...</p>;
  if (error) return <p>{error.message}</p>;

  return (
    <ListGroup>
      {tracks.map(track => (
        <TrackItem key={track.id} track={track} favorite={false} />
      ))}
    </ListGroup>
  );
}
