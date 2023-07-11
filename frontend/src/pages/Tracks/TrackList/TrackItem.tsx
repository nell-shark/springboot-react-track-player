import { useEffect } from 'react';
import { Button } from 'react-bootstrap';
import ListGroup from 'react-bootstrap/ListGroup';

import { BASE_URL } from '@data/constants';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { userService } from '@services/userService';

import { playNextTrack, togglePlayTrack } from '@store/slices';
import { addFavoriteTrack } from '@store/slices/userSlice';

import { Track } from '@typings/track';

import { audio, playAudioElement } from '@utils/trackUtils';

import { faPause, faPlay } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


interface TrackItemProps {
  track: Track;
}

export function TrackItem({ track }: TrackItemProps) {
  const dispatch = useAppDispatch();
  const trackPlayerState = useAppSelector(state => state.trackPlayer);
  const userState = useAppSelector(state => state.user);

  async function addFavoriteTrackToUser() {
    if (!userState.user) {
      window.open(BASE_URL + '/oauth2/authorization/github', '_self');
      return;
    }
    dispatch(addFavoriteTrack(track));
    await userService.addFavoriteTrack(track.id);
  }

  function play() {
    dispatch(togglePlayTrack(track));
    audio.addEventListener('ended', () => dispatch(playNextTrack()));
    trackPlayerState.playing ? audio.pause() : playAudioElement(track.id);
  }

  useEffect(() => {
    if (trackPlayerState.track === track && trackPlayerState.playing) {
      playAudioElement(trackPlayerState.track.id);
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
        {/*<FontAwesomeIcon icon={userState.user!.favoriteTracks.find(t => t.id === track.id) ? like : unlike} />*/}
      </Button>
    </ListGroup.Item>
  );
}
