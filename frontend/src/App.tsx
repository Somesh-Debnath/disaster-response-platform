import { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import HomePage from './pages/HomePage';
import DisastersPage from './pages/DisastersPage';
import MapPage from './pages/MapPage';
import { connectWebSocket, disconnectWebSocket } from './services/websocket';
import { disastersState, socialMediaState } from './state/atoms';
import './App.css'

function App() {
  const setDisasters = useSetRecoilState(disastersState);
  const setSocialMediaReports = useSetRecoilState(socialMediaState);

  useEffect(() => {
    const handleMessage = (message: any) => {
      console.log('Received message:', message);
      // Here, you would have logic to determine the type of message
      // and update the appropriate state.
      if (message.type === 'DISASTER_UPDATE') {
        setDisasters(prev => [...prev.filter(d => d.id !== message.payload.id), message.payload]);
      }
      if (message.type === 'SOCIAL_MEDIA_UPDATE') {
        setSocialMediaReports(prev => [message.payload, ...prev]);
      }
    };
    
    connectWebSocket(handleMessage);

    return () => {
      disconnectWebSocket();
    };
  }, [setDisasters, setSocialMediaReports]);

  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/disasters" element={<DisastersPage />} />
        <Route path="/map" element={<MapPage />} />
      </Routes>
    </Router>
  );
}

export default App;
