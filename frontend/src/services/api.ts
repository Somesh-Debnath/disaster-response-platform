import axios from 'axios';
import { Disaster, Resource, SocialMediaReport, OfficialUpdate } from '../state/atoms';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getDisasters = (): Promise<Disaster[]> => {
  return apiClient.get('/disasters').then(res => res.data);
};

export const createDisaster = (disasterData: Omit<Disaster, 'id' | 'createdAt' | 'updatedAt'>): Promise<Disaster> => {
  return apiClient.post('/disasters', disasterData).then(res => res.data);
};

export const getResourcesForDisaster = (disasterId: string): Promise<Resource[]> => {
  return apiClient.get(`/disasters/${disasterId}/resources`).then(res => res.data);
};

export const getSocialMediaReports = (disasterId: string): Promise<SocialMediaReport[]> => {
  return apiClient.get(`/disasters/${disasterId}/social-media`).then(res => res.data);
};

export const getOfficialUpdates = (disasterId: string): Promise<OfficialUpdate[]> => {
  return apiClient.get(`/disasters/${disasterId}/official-updates`).then(res => res.data);
}; 