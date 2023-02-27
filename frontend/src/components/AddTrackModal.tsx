import { ChangeEvent, FormEvent, MouseEvent, useRef, useState } from 'react';

import { AxiosError } from 'axios';
import Button from 'react-bootstrap/Button';
import { Form } from 'react-bootstrap';
import Modal from 'react-bootstrap/Modal';
import { trackService } from '@services/TrackService';

interface AddTrackModalProps {
  show: boolean;
  handleClose: () => void;
}

export function AddTrackModal({ show, handleClose }: AddTrackModalProps) {
  const trackNameRef = useRef<HTMLInputElement | null>(null);
  const [track, setTrack] = useState<File | null>(null);
  const [isLoading, setLoading] = useState(false);
  const [error, setError] = useState<string>('');

  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setError('');

    const name = trackNameRef?.current?.value.trim();
    if (!name?.length || !track) return;

    setLoading(true);

    try {
      await trackService.addTrack(name, track);
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
            onChange={(e: ChangeEvent<HTMLInputElement>) => setTrack(e.target.files?.item(0)!)}
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
