import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const SOCKET_URL = 'http://localhost:8080/ws';

let stompClient: Client;

export const connectWebSocket = (onMessage: (message: any) => void) => {
  stompClient = new Client({
    webSocketFactory: () => new SockJS(SOCKET_URL),
    onConnect: () => {
      console.log('Connected to WebSocket');
      
      // Subscribe to a general topic or specific topics as needed
      stompClient.subscribe('/topic/disasters', (message) => {
        onMessage(JSON.parse(message.body));
      });
      stompClient.subscribe('/topic/social-media', (message) => {
        onMessage(JSON.parse(message.body));
      });
    },
    onStompError: (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    },
  });

  stompClient.activate();
};

export const disconnectWebSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
    console.log('Disconnected from WebSocket');
  }
}; 