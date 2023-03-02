import { AddTrackModal } from './AddTrackModal';
import Button from 'react-bootstrap/Button';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMusic } from '@fortawesome/free-solid-svg-icons';
import { useState } from 'react';
import {Track} from "@interfaces/track";

interface AddTrackProps {
  addTrack: (track: Track) => void;
}

export function AddTrack({addTrack}: AddTrackProps) {
  const [show, setShow] = useState(false);

  function handleClose() {
    setShow(false);
  }
  
  function handleShow() {
    setShow(true);
  }

  return (
    <div className="text-center mt-4">
      <Button variant="outline-dark" onClick={handleShow}>
        Add
        <FontAwesomeIcon icon={faMusic} color="purple" className="px-1" />
      </Button>

      {show && <AddTrackModal show={show} handleClose={handleClose} addTrack={addTrack} />}
    </div>
  );
}
