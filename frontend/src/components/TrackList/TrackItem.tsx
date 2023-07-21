import { useEffect } from 'react';
import { Button } from 'react-bootstrap';
import ListGroup from 'react-bootstrap/ListGroup';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { userService } from '@services/userService';

import { playNextTrack, togglePlayTrack } from '@store/slices';
import { toggleFavoriteTrack } from '@store/slices/userSlice';

import { Track } from '@typings/track';

import { audio, pauseAudioElement, playAudioElement } from '@utils/trackUtils';

import { faHeart as unlike } from '@fortawesome/free-regular-svg-icons';
import { faPause, faPlay, faHeart as like } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

interface TrackItemProps {
  track: Track;
}

export function TrackItem({ track }: TrackItemProps) {
  const dispatch = useAppDispatch();
  const trackPlayerState = useAppSelector(state => state.trackPlayer);
  const userState = useAppSelector(state => state.user);

  async function addFavoriteTrackToUser() {
    dispatch(toggleFavoriteTrack(track));
    if (!userState.user!.favoriteTracks.find(t => t.id === track.id)) await userService.addFavoriteTrack(track.id);
    else await userService.removeFavoriteTrack(track.id);
  }

  function play() {
    dispatch(togglePlayTrack(track));
    audio.addEventListener('ended', () => dispatch(playNextTrack()));
    trackPlayerState.playing ? pauseAudioElement() : playAudioElement(track.id);
  }

  useEffect(() => {
    if (trackPlayerState.track === track && trackPlayerState.playing) {
      void playAudioElement(trackPlayerState.track.id);
    }
  });

  return (
    <ListGroup.Item className='d-flex justify-content-between'>
      <div>
        <Button variant='outline-link' className='border-0' onClick={play}>
          <FontAwesomeIcon icon={trackPlayerState.track === track && trackPlayerState.playing ? faPause : faPlay} />
        </Button>
        {track.name}
      </div>
      <Button variant='outline-link' className='border-0' onClick={addFavoriteTrackToUser}>
        {userState.user && (
          <FontAwesomeIcon icon={userState.user!.favoriteTracks.find(t => t.id === track.id) ? like : unlike} />
        )}
      </Button>
    </ListGroup.Item>
  );
}
