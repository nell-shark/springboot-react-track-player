import { useEffect } from 'react';
import { Button } from 'react-bootstrap';
import ListGroup from 'react-bootstrap/ListGroup';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { playNextTrack, playTrack } from '@store/slices';

import { Track } from '@typings/track';

import { audio, playAudioElement } from '@utils/trackUtils';

import { faHeart as unlike } from '@fortawesome/free-regular-svg-icons';
import { faPause, faPlay, faHeart as like } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

interface TrackItemProps {
  track: Track;
  favorite: boolean;
}

export function TrackItem({ track, favorite }: TrackItemProps) {
  const dispatch = useAppDispatch();
  const state = useAppSelector(state => state.trackPlayer);

  async function addFavoriteTrackToUser() {
  }

  function play() {
    dispatch(playTrack(track));
    audio.addEventListener('ended', () => dispatch(playNextTrack()));
    state.playing ? audio.pause() : playAudioElement(track.id);
  }

  useEffect(() => {
    if (state.track === track && state.playing) {
      playAudioElement(state.track.id);
    }
  });

  return (
    <ListGroup.Item className='d-flex justify-content-between'>
      <div>
        <Button variant='outline-link' className='border-0' onClick={play}>
          <FontAwesomeIcon icon={state.track === track && state.playing ? faPause : faPlay} />
        </Button>
        {track.name}
      </div>
      <Button variant='outline-link' onClick={addFavoriteTrackToUser} className='border-0'>
        <FontAwesomeIcon icon={favorite ? like : unlike} />
      </Button>
    </ListGroup.Item>
  );
}
