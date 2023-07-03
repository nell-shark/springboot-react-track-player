import { Container } from 'react-bootstrap';
import { Navigate, Route, Routes } from 'react-router-dom';

import { Footer } from '@components/Footer';
import { Navbar } from '@components/Navbar';
import { TrackPlayer } from '@components/TrackPlayer';

import { About } from '@pages/About';
import { NotFound } from '@pages/NotFound';
import { Tracks } from '@pages/Tracks';

export default function App() {
  return (
    <>
      <Navbar />
      <main>
        <Container>
          <Routes>
            <Route path='/' element={<Navigate to='/tracks' />} />
            <Route path='/tracks' element={<Tracks title='Tracks' />} />
            <Route path='/about' element={<About title='About' />} />
            <Route path='*' element={<NotFound title={'NotFound'} />} />
          </Routes>
        </Container>
      </main>
      <TrackPlayer isActive={true} isPlaying={false} />
      <Footer />
    </>
  );
}