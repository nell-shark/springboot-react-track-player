import {faHeart as like} from '@fortawesome/free-solid-svg-icons';
import {faHeart as unlike} from '@fortawesome/free-regular-svg-icons'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import ListGroup from 'react-bootstrap/ListGroup';
import {Track} from "@interfaces/track";
import {axiosInstance} from "@services/axios-instance";
import {Button} from "react-bootstrap";

export interface TrackItemProps {
    track: Track;
    favorite: boolean;
}

export function TrackItem({
                              track,
                              favorite,
                          }: TrackItemProps) {
    async function addFavoriteTrackToUser() {
        await axiosInstance.post("/api/v1/users/oauth2/favorite/track",
            {"trackId": track.id}
        )
    }

    return (
        <ListGroup.Item>
            {track.name}
            {favorite ?
                <FontAwesomeIcon icon={like}/>
                : <Button variant="outline-link" onClick={addFavoriteTrackToUser}>
                    <FontAwesomeIcon icon={unlike}/>
                </Button>}
        </ListGroup.Item>
    );
}
