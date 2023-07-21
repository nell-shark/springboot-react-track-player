import { useState } from 'react';
import Button from 'react-bootstrap/Button';

import { AddTrackModal } from '@pages/Tracks/AddTrack/AddTrackModal';

import { faMusic } from '@fortawesome/free-solid-svg-icons/faMusic';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export function AddTrack() {
  const [show, setShow] = useState(false);

  function handleShow() {
    setShow(() => true);
  }

  function handleClose() {
    setShow(() => false);
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
