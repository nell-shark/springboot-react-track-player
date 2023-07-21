import { AxiosError } from 'axios';

import { ChangeEvent, FormEvent, useRef, useState } from 'react';
import { Form, Modal } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';

import { trackService } from '@services/trackService';

interface AddTrackModalProps {
  show: boolean;
  handleClose: () => void;
}

export function AddTrackModal({ show, handleClose }: AddTrackModalProps) {
  const [file, setFile] = useState<File | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>('');
  const trackNameRef = useRef<HTMLInputElement | null>(null);

  function handleChange(e: ChangeEvent<HTMLInputElement>) {
    if (e.target.files?.item(0)) setFile(() => e.target.files!.item(0));
  }

  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    setIsLoading(true);
    try {
      e.preventDefault();
      const name: string = trackNameRef?.current!.value.trim();
      await trackService.uploadTrack(name, file!);
      window.location.reload();
    } catch (error) {
      console.log(error);
      setError(() => (error instanceof AxiosError ? error.message : 'Something went wrong'));
    }
    setIsLoading(false);
  }

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Add Track</Modal.Title>
      </Modal.Header>
      <Form onSubmit={handleSubmit}>
        <Modal.Body>
          <Form.Control type='text' placeholder='Name' className='mb-2' ref={trackNameRef} />
          <Form.Control type='file' className='mb-2' accept='audio/mpeg' onChange={handleChange} />
        </Modal.Body>
        <Modal.Footer>
          {error && <p>{error}</p>}
          <Button type='submit' variant='outline-primary' disabled={isLoading}>
            {isLoading ? 'Loading…' : 'Upload'}
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
}
