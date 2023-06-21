import { Button } from 'react-bootstrap';
import ListGroup from 'react-bootstrap/ListGroup';

import { TrackInfo } from '@interfaces/track';

import { axiosInstance } from '@services/axios-instance';
import { trackService } from '@services/TrackService';

import { faHeart as unlike } from '@fortawesome/free-regular-svg-icons';
import { faHeart as like, faPlay } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface TrackItemProps {
  track: TrackInfo;
  favorite: boolean;
}

export function TrackItem({ track, favorite }: TrackItemProps) {
  async function addFavoriteTrackToUser() {
    await axiosInstance.post('/api/v1/users/oauth2/favorite/track', {
      trackId: track.id
    });
  }

  async function play() {
    console.log('item clicked');
    const { data } = await trackService.getTrackById(track.id);
    console.log(data.id);
    let audio = new Audio('http://streaming.tdiradio.com:8000/house.mp3');
    await audio.play();
  }

  return (
    <ListGroup.Item className='d-flex justify-content-between'>
      <div>
        <Button variant='outline-link' className='border-0' onClick={play}>
          <FontAwesomeIcon icon={faPlay} />
        </Button>
        {track.name}
      </div>
      {favorite ? (
        <FontAwesomeIcon icon={like} />
      ) : (
        <Button variant='outline-link' onClick={addFavoriteTrackToUser} className='border-0'>
          <FontAwesomeIcon icon={unlike} />
        </Button>
      )}
    </ListGroup.Item>
  );
}
