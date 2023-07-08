import { Button, Container } from 'react-bootstrap';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { nextTrack, prevTrack } from '@store/slices';

import { faBackwardStep, faForwardStep, faPause, faPlay } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


export function TrackPlayer() {
  const dispatch = useAppDispatch();
  const state = useAppSelector(state => state.trackPlayer);

  if (state.disabled) return null;

  function previous() {
    dispatch(prevTrack());
  }

  function play() {}

  function next() {
    dispatch(nextTrack);
  }

  return (
    <div className='fixed-bottom w-100 bg-light border-top'>
      <Container className='d-flex align-items-center'>
        <Button variant='outline-link' className='border-0' active={state.hasPrevTrack} onClick={previous}>
          <FontAwesomeIcon icon={faBackwardStep} />
        </Button>
        <Button variant='outline-link' className='border-0' onClick={play}>
          <FontAwesomeIcon icon={state.playing ? faPause : faPlay} size='2x' />
        </Button>
        <Button variant='outline-link' className='border-0' active={state.hasNextTrack} onClick={next}>
          <FontAwesomeIcon icon={faForwardStep} />
        </Button>
      </Container>
    </div>
  );
}
