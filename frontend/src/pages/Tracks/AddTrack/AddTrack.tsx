import Button from 'react-bootstrap/Button';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { trackService } from '@services/trackService';

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

  async function play() {
    // dispatch(playTrack());
    const audio = new Audio();
    const { data } = await trackService.testTrack('64c93cfc-1f56-4fa8-930b-5b85fffe320b');
    console.log('blob');
    const audioBlob = new Blob([data], { type: 'audio/mp3' });
    console.log('src');
    audio.src = window.URL.createObjectURL(audioBlob);
    await audio.play();
  }

  return (
    <>
      <Button onClick={play}>PLAY</Button>
      {/*<Button variant='outline-dark' onClick={handleShow} className='add-track'>*/}
      {/*  Add*/}
      {/*  <FontAwesomeIcon icon={faMusic} color='purple' className='px-1' />*/}
      {/*</Button>*/}
      {/*<AddTrackModal show={show} handleClose={handleClose} />*/}
    </>
  );
}