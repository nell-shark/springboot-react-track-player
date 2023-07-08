import { Button } from 'react-bootstrap';
import ListGroup from 'react-bootstrap/ListGroup';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { axiosInstance } from '@services/axiosInstance';

import { pauseTrack, playTrack } from '@store/slices';

import { Track } from '@typings/track';

import { pauseAudioElement, playAudioElement } from '@utils/trackUtils';

import { faHeart as unlike } from '@fortawesome/free-regular-svg-icons';
import { faPause, faPlay, faHeart as like } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


export interface TrackItemProps {
  track: Track;
  favorite: boolean;
}

export function TrackItem({ track, favorite }: TrackItemProps) {
  const dispatch = useAppDispatch();
  const state = useAppSelector(state => state.trackPlayer);

  async function addFavoriteTrackToUser() {
    await axiosInstance.post('/api/v1/users/oauth2/favorite/track', {
      trackId: track.id
    });
  }

  function play() {
    if (state.disabled || !state.playing) {
      dispatch(playTrack(track));
      playAudioElement(track.id);
    } else if (state.playing) {
      dispatch(pauseTrack());
      pauseAudioElement();
    }
  }

  return (
    <ListGroup.Item className='d-flex justify-content-between'>
      <div>
        <Button variant='outline-link' className='border-0' onClick={play}>
          <FontAwesomeIcon icon={state.disabled || !state.playing ? faPlay : faPause} />
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
