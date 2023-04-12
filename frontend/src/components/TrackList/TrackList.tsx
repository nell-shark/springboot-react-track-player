import ListGroup from 'react-bootstrap/ListGroup';
import {TrackItem} from '@components/TrackList/TrackItem';
import {Track} from "@interfaces/track";

interface TrackListProps {
  isLoading: boolean;
  tracks: Track[];
  error?: Error;
}

export function TrackList({isLoading, tracks, error}: TrackListProps) {
  if (isLoading) return (<p>Loading...</p>);
  if (error) return (<p>{error.message}</p>);

  return (
    <ListGroup>
            {tracks.map((track) => (
              <TrackItem
                key={track.id}
                track={track}
                favorite={false}
              />
            ))}
    </ListGroup>
  );
}
