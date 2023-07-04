import { Button, Container } from 'react-bootstrap';

import { useAppSelector } from '@hooks/redux';

import { faBackwardStep, faForwardStep, faPause, faPlay } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export function TrackPlayer() {
  const status = useAppSelector(state => state.trackPlayer.status);
  if (status === 'disabled') return null;

  return (
    <div className='fixed-bottom w-100 bg-light border-top'>
      <Container className='d-flex align-items-center'>
        <Button variant='outline-link' className='border-0' active={false}>
          <FontAwesomeIcon icon={faBackwardStep} />
        </Button>
        <Button variant='outline-link' className='border-0'>
          {status === 'playing' && <FontAwesomeIcon icon={faPause} size='2x' />}
          {status === 'pause' && <FontAwesomeIcon icon={faPlay} size='2x' />}
        </Button>
        <Button variant='outline-link' className='border-0' active={false}>
          <FontAwesomeIcon icon={faForwardStep} />
        </Button>
      </Container>
    </div>
  );
}