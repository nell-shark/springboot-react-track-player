import { Container } from 'react-bootstrap';
import { Navigate, Route, Routes } from 'react-router-dom';

import { Footer } from '@components/Footer';
import { Navbar } from '@components/Navbar';

import { About } from '@pages/About';
import { FavoriteTracks } from '@pages/Favorite';
import { NotFound } from '@pages/NotFound';
import { Tracks } from '@pages/Tracks';

export function App() {
  return (
    <>
      <Navbar />
      <main>
        <Container>
          <Routes>
            <Route path='/' element={<Navigate to='/tracks' />} />
            <Route path='/tracks' element={<Tracks title='Tracks' />} />
            <Route path='/about' element={<About title='About' />} />
            <Route path='/favorite/tracks' element={<FavoriteTracks title='Favorite Tracks' />} />
            <Route path='*' element={<NotFound title={'NotFound'} />} />
          </Routes>
        </Container>
      </main>
      <Footer />
    </>
  );
}
