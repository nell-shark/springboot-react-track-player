import { useState } from 'react';
import Button from 'react-bootstrap/Button';

import { faMusic } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { AddTrackModal } from './AddTrackModal';

export function AddTrack() {
  const [show, setShow] = useState(false);

  function handleClose() {
    setShow(false);
  }

  function handleShow() {
    setShow(true);
  }

  return (
    <>
      <Button variant='outline-dark' onClick={handleShow} className='add-track'>
        Add
        <FontAwesomeIcon icon={faMusic} color='purple' className='px-1' />
      </Button>
      <AddTrackModal show={show} handleClose={handleClose} />
    </>
  );
}
