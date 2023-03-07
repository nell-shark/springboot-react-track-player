import ListGroup from 'react-bootstrap/ListGroup';
import {TrackItem} from '@components/TrackList/TrackItem';
import {Track} from "@interfaces/track";

interface TrackListProps {
  isFetching: boolean;
  tracks: Track[];
  error?: string;
}

export function TrackList({isFetching, tracks, error}: TrackListProps) {
  return (
    <>
      <ListGroup>
        {tracks.map((track) => (
          <TrackItem
            key={track.id}
            track={track}
            favorite={false}
          />
        ))}
      </ListGroup>
      {isFetching && <p>Loading...</p>}
      {error && <p>error</p>}
    </>
  );
}
