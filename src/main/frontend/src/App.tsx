import { Route, Routes } from 'react-router-dom';
import { About } from '@pages/About';
import { Tracks } from '@pages/Tracks';

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Tracks title="Tracks" />}></Route>
      <Route path="/about" element={<About title="About" />}></Route>
    </Routes>
  );
}
