import {ChangeEvent, FormEvent, useRef, useState} from 'react';

import {AxiosError} from 'axios';
import Button from 'react-bootstrap/Button';
import {Form} from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal';
import {trackService} from '@services/TrackService';
import {Track} from "@interfaces/track";

interface AddTrackModalProps {
  show: boolean;
  handleClose: () => void;
  addTrack: (track: Track) => void;
}

export function AddTrackModal({show, handleClose, addTrack}: AddTrackModalProps) {
  const trackNameRef = useRef<HTMLInputElement | null>(null);
  const [file, setFile] = useState<File | null>(null);
  const [isLoading, setLoading] = useState(false);
  const [error, setError] = useState<string>('');

  function handleChange(e: ChangeEvent<HTMLInputElement>) {
    setFile(e.target.files?.item(0)!)
  }

  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setError('');

    const name = trackNameRef?.current?.value.trim();
    if (!name?.length || !file) return;

    setLoading(true);

    try {
      const {data:trackId}  = await trackService.addTrack(name, file);
      const {data: track} = await trackService.getTrackById(trackId);
      addTrack(track);
      handleClose();
    } catch (err) {
      const e = err as AxiosError | Error;
      setError(e.message);
    }

    setLoading(false);
  }


  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Add Track</Modal.Title>
      </Modal.Header>
      <Form onSubmit={handleSubmit}>
        <Modal.Body>
          <Form.Control
            type="text"
            placeholder="Name"
            className="mb-2"
            ref={trackNameRef}
          />
          <Form.Control
            type="file"
            className="mb-2"
            accept="audio/mpeg, audio/mp"
            onChange={handleChange}
          />
        </Modal.Body>
        <Modal.Footer>
          {error && <p>{error}</p>}
          <Button type="submit" variant="outline-primary" disabled={isLoading}>
            {isLoading ? 'Loading…' : 'Upload'}
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
}
