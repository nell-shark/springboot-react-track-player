import { faHeart as Favorite } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import ListGroup from 'react-bootstrap/ListGroup';

export interface TrackItemProps {
    id: number,
    name: string,
    seconds: number,
    data: any,
    favorite: boolean
}

export function TrackItem({ id, name, seconds, data, favorite }: TrackItemProps) {
    return (
        <ListGroup.Item>
            {name}
            {favorite && <FontAwesomeIcon icon={Favorite} />}
        </ListGroup.Item>
    );
}
