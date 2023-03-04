import {Route, Routes} from 'react-router-dom';

import {About} from '@pages/About';
import {Container} from 'react-bootstrap';
import {Footer} from '@components/Footer/Footer';
import {Navbar} from '@components/Navbar/Navbar';
import {Tracks} from '@pages/Tracks';
import {NotFound} from "@pages/NotFound";

export default function App() {
  return (
    <>
      <Navbar/>
      <main>
        <Container>
          <Routes>
            <Route path="/" element={<Tracks title="Tracks"/>}/>
            <Route path="/tracks" element={<Tracks title="Tracks"/>}/>
            <Route path="/about" element={<About title="About"/>}/>
            <Route path="*" element={<NotFound title={"NotFound"}/>}/>
          </Routes>
        </Container>
      </main>
      <Footer/>
    </>
  );
}
