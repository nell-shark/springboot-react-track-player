import {faHeart as Favorite} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import ListGroup from 'react-bootstrap/ListGroup';
import {Track} from "@interfaces/track";

export interface TrackItemProps {
  track: Track
  favorite: boolean;
}

export function TrackItem({
                            track,
                            favorite,
                          }: TrackItemProps) {
  return (
    <ListGroup.Item>
      {track.name}
      {favorite && <FontAwesomeIcon icon={Favorite}/>}
    </ListGroup.Item>
  );
}
