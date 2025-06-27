import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import Router from './routes/Router';
import { WebSocketProvider } from './contexts/WebSocketContext';

function App() {
  return (
    <BrowserRouter>
      <WebSocketProvider>
        <Router />
      </WebSocketProvider>
    </BrowserRouter>
  );
}

export default App;
