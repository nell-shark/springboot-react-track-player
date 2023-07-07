import { useAppDispatch, useAppSelector } from '@hooks/redux';

export function AddTrack() {
  const track = useAppSelector(state => state.trackPlayer.track);
  const dispatch = useAppDispatch();
  // const [show, setShow] = useState(false);
  //
  // function handleClose() {
  //   setShow(false);
  // }
  //
  // function handleShow() {
  //   setShow(true);
  // }

  return (
    <>
      {/*<Button variant='outline-dark' onClick={handleShow} className='add-track'>*/}
      {/*  Add*/}
      {/*  <FontAwesomeIcon icon={faMusic} color='purple' className='px-1' />*/}
      {/*</Button>*/}
      {/*<AddTrackModal show={show} handleClose={handleClose} />*/}
    </>
  );
}