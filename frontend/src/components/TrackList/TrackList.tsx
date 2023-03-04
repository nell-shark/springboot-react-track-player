import ListGroup from 'react-bootstrap/ListGroup';
import {TrackItem} from '@components/TrackList/TrackItem';
import {Track} from "@interfaces/track";

interface TrackListProps {
  loading: boolean;
  error: string;
  tracks: Track[];
}

export function TrackList({loading, error, tracks}: TrackListProps) {
  return (
    <>
      {loading && <p>Loading...</p>}
      {error && <p>{error}</p>}
      <ListGroup>
        {tracks.map((track) => (
          <TrackItem
            key={track.id}
            track={track}
            favorite={false}
          />
        ))}
      </ListGroup>
    </>
  );
}
