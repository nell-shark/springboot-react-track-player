import { Button, Container } from 'react-bootstrap';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { nextTrack, pauseTrack, playTrack, previousTrack } from '@store/slices';

import { faBackwardStep, faForwardStep, faPause, faPlay } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


export function TrackPlayer() {
  const state = useAppSelector(state => state.trackPlayer);
  const dispatch = useAppDispatch();

  if (state.status === 'disabled') return null;

  function previous() {
    dispatch(previousTrack());
  }

  function play() {
    switch (state.status) {
      case 'play':
        dispatch(playTrack(state.track!));
        break;
      case 'pause':
        dispatch(pauseTrack());
        break;
    }
  }

  function next() {
    dispatch(nextTrack);
  }

  return (
    <div className='fixed-bottom w-100 bg-light border-top'>
      <Container className='d-flex align-items-center'>
        <Button variant='outline-link' className='border-0' active={state.hasPrevious} onClick={previous}>
          <FontAwesomeIcon icon={faBackwardStep} />
        </Button>
        <Button variant='outline-link' className='border-0' onClick={play}>
          {state.status === 'play' && <FontAwesomeIcon icon={faPause} size='2x' />}
          {state.status === 'pause' && <FontAwesomeIcon icon={faPlay} size='2x' />}
        </Button>
        <Button variant='outline-link' className='border-0' active={state.hasNext} onClick={next}>
          <FontAwesomeIcon icon={faForwardStep} />
        </Button>
      </Container>
    </div>
  );
}