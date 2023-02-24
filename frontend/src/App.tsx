import { Navigate, Route, Routes } from 'react-router-dom';

import { About } from '@pages/About';
import { Container } from 'react-bootstrap';
import { Footer } from './components/Footer';
import { Navbar } from '@components/Navbar';
import { Tracks } from '@pages/Tracks';

export default function App() {
  return (
    <>
      <Navbar />
      <main>
        <Container>
          <Routes>
            <Route path="/" element={<Tracks title="Tracks" />} />
            <Route path="/about" element={<About title="About" />} />
            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </Container>
      </main>
      <Footer />
    </>
  );
}
