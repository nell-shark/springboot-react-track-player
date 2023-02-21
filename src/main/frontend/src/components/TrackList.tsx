import { Container } from 'react-bootstrap';
import ListGroup from 'react-bootstrap/ListGroup';
import { TrackItem } from '@components/TrackItem';
import { useTracks } from '../hooks/useTracks';

export function TrackList() {
  const {loading, error, tracks} = useTracks();

  return (
    <main>
      <Container>
        {loading && <p>Loading...</p>}
        {error && <p>{error}</p>}
        <ListGroup>
          {tracks.map((track) => (
            <TrackItem
              key={track.id}
              id={track.id}
              name={track.name}
              seconds={0}
              data={undefined}
              favorite={false}
            />
          ))}
        </ListGroup>
      </Container>
    </main>
  );
}
