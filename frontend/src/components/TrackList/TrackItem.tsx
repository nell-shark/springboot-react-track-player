import { useEffect } from 'react';
import { Button } from 'react-bootstrap';
import ListGroup from 'react-bootstrap/ListGroup';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { userService } from '@services/userService';

import { playTrack } from '@store/slices';
import { toggleFavoriteTrack } from '@store/slices/userSlice';

import { TrackInfo } from '@typings/track';

import { audio, pauseAudioElement, playAudioElement } from '@utils/trackUtils';

import { faHeart as unlike } from '@fortawesome/free-regular-svg-icons';
import { faPause, faPlay, faHeart as like } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

interface TrackItemProps {
  trackInfo: TrackInfo;
}

export function TrackItem({ trackInfo }: TrackItemProps) {
  const dispatch = useAppDispatch();
  const trackPlayerState = useAppSelector(state => state.trackPlayer);
  const userState = useAppSelector(state => state.user);

  async function addFavoriteTrackToUser() {
    dispatch(toggleFavoriteTrack(trackInfo));
    if (!userState.user!.favoriteTracks.find(t => t.id === trackInfo.id))
      await userService.addFavoriteTrack(trackInfo.id);
    else await userService.removeFavoriteTrack(trackInfo.id);
  }

  function play() {
    dispatch(playTrack(trackInfo));
    trackPlayerState.isPlaying ? pauseAudioElement() : playAudioElement(trackInfo.id);

    const index = trackPlayerState.trackListPage?.tracks.findIndex(t => trackInfo.id === t.id) || -1;
    const hasNextTrack = trackPlayerState.trackListPage!.tracks.length - 1 > index;
    if (hasNextTrack) {
      const nextTrack = trackPlayerState.trackListPage?.tracks[index + 1]!;
      audio.addEventListener('ended', () => dispatch(playTrack(nextTrack)));
    }
  }

  useEffect(() => {
    if (trackPlayerState.track === trackInfo && trackPlayerState.isPlaying) {
      void playAudioElement(trackPlayerState.track.id);
    }
  });

  return (
    <ListGroup.Item className='d-flex justify-content-between'>
      <div>
        <Button variant='outline-link' className='border-0' onClick={play}>
          <FontAwesomeIcon
            icon={trackPlayerState.track === trackInfo && trackPlayerState.isPlaying ? faPause : faPlay}
          />
        </Button>
        {trackInfo.name}
      </div>
      <Button variant='outline-link' className='border-0' onClick={addFavoriteTrackToUser}>
        {userState.user && (
          <FontAwesomeIcon icon={userState.user!.favoriteTracks.find(t => t.id === trackInfo.id) ? like : unlike} />
        )}
      </Button>
    </ListGroup.Item>
  );
}
