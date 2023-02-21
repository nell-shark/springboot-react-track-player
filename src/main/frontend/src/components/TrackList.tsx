import { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import ListGroup from "react-bootstrap/ListGroup";
import { Track } from "interfaces/track";
import { TrackService } from "@services/TrackService";
import { TrackItem } from "@components/TrackItem";

const trackService: TrackService = TrackService.getInstance();

export function TrackList() {
  const [tracks, setTracks] = useState<Track[]>([]);

  async function fetchTracks() {
    const response = await trackService.getAllTracks();
    setTracks(response.data);
  }

  useEffect(() => {
    fetchTracks();
  }, []);

  return (
    <main>
      <Container>
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
