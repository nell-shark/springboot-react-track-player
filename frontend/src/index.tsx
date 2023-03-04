import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';

import App from './App';
import {BrowserRouter} from 'react-router-dom';
import React from 'react';
import ReactDOM from 'react-dom/client';
import {ErrorBoundary} from "@components/ErrorBoundary";

ReactDOM.createRoot(document.getElementById('root')!).render(
  <ErrorBoundary>
    <React.StrictMode>
      <BrowserRouter>
        <App/>
      </BrowserRouter>
    </React.StrictMode>
  </ErrorBoundary>
);
