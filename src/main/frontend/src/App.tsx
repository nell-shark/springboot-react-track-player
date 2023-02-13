import { Route, Routes } from 'react-router-dom';
import { AboutPage } from '@pages/AboutPage';
import { TracksPage } from '@pages/TracksPage';

export default function App() {
  return (
    <Routes>
      <Route
        path='/'
        element={<TracksPage title='Tracks' />} >
      </Route>
      <Route
        path='/about'
        element={<AboutPage title='About' />} >
      </Route>
    </Routes>
  );
}
