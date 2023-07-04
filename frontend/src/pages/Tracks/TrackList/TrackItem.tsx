import { Track } from '@typings/track';

import { Button } from 'react-bootstrap';
import ListGroup from 'react-bootstrap/ListGroup';

import { useAppDispatch } from '@hooks/redux';

import { axiosInstance } from '@services/axiosInstance';
import { trackService } from '@services/trackService';

import { playTrack } from '@store/slices';

import { faHeart as unlike } from '@fortawesome/free-regular-svg-icons';
import { faPlay, faHeart as like } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface TrackItemProps {
  track: Track;
  favorite: boolean;
}

export function TrackItem({ track, favorite }: TrackItemProps) {
  const dispatch = useAppDispatch();

  async function addFavoriteTrackToUser() {
    await axiosInstance.post('/api/v1/users/oauth2/favorite/track', {
      trackId: track.id
    });
  }

  async function play() {
    console.log('item clicked');
    const { data } = await trackService.getTrackById(track.id);
    console.log(data.id);

    dispatch(playTrack(data.id));
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