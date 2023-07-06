interface AddTrackModalProps {
  show: boolean;
  handleClose: () => void;
}

export function AddTrackModal({ show, handleClose }: AddTrackModalProps) {
  return null;
  // const trackNameRef = useRef<HTMLInputElement | null>(null);
  // const [file, setFile] = useState<File | null>(null);
  //
  // function getMutationFn(name: string, file: File) {
  //   return trackService.uploadTrack(name, file);
  // }
  //
  // function handleChange(e: ChangeEvent<HTMLInputElement>) {
  //   if (e.target.files?.item(0)) setFile(e.target.files.item(0));
  // }
  //
  // function handleSubmit(e: FormEvent<HTMLFormElement>) {
  //   e.preventDefault();
  //
  //   const name = trackNameRef?.current?.value.trim();
  //   if (!name || !name.length || !file) return;
  //
  //   upload({ name, file });
  // }
  //
  // function handleSuccess() {
  //   queryClient.invalidateQueries([TRACKS]);
  //   handleClose();
  // }
  //
  // return (
  //   <Modal show={show} onHide={handleClose} centered>
  //     <Modal.Header closeButton>
  //       <Modal.Title>Add Track</Modal.Title>
  //     </Modal.Header>
  //     <Form onSubmit={handleSubmit}>
  //       <Modal.Body>
  //         <Form.Control type='text' placeholder='Name' className='mb-2' ref={trackNameRef} />
  //         <Form.Control type='file' className='mb-2' accept='audio/mpeg' onChange={handleChange} />
  //       </Modal.Body>
  //       <Modal.Footer>
  //         {isError && <p>{error.message}</p>}
  //         <Button type='submit' variant='outline-primary' disabled={isLoading}>
  //           {isLoading ? 'Loading…' : 'Upload'}
  //         </Button>
  //       </Modal.Footer>
  //     </Form>
  //   </Modal>
  // );
}