import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';

import App from './App';
import { BrowserRouter } from 'react-router-dom';
import { ErrorBoundary } from '@components/ErrorBoundary';
import React from 'react';
import ReactDOM from 'react-dom/client';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter>
      <ErrorBoundary>
        <App />
      </ErrorBoundary>
    </BrowserRouter>
  </React.StrictMode>
);
