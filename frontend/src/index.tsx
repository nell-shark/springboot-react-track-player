import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';

import App from './App';
import {BrowserRouter} from 'react-router-dom';
import React from 'react';
import ReactDOM from 'react-dom/client';
import {ErrorBoundary} from "@components/ErrorBoundary";
import {QueryClient, QueryClientProvider} from '@tanstack/react-query';
import {ReactQueryDevtools} from '@tanstack/react-query-devtools';

const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
    <ErrorBoundary>
        <BrowserRouter>
            <QueryClientProvider client={queryClient}>
                <App/>
                <ReactQueryDevtools/>
            </QueryClientProvider>
        </BrowserRouter>
    </ErrorBoundary>
);
