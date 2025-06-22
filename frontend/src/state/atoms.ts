import { atom } from 'recoil';

// Define interfaces for our data structures
export interface Disaster {
  id: string;
  title: string;
  locationName: string;
  latitude: number;
  longitude: number;
  description: string;
  tags: string[];
  ownerId: string;
  createdAt: string;
  updatedAt: string;
}

export interface Resource {
  id: string;
  disasterId: string;
  name: string;
  locationName: string;
  latitude: number;
  longitude: number;
  type: string;
  createdAt: string;
}

export interface SocialMediaReport {
    id: string;
    post: string;
    user: string;
    platform: string;
    timestamp: string;
}

export interface OfficialUpdate {
    id: string;
    source: string;
    title: string;
    content: string;
    url: string;
    publishedAt: string;
}

// Define atoms for storing our application state
export const disastersState = atom<Disaster[]>({
  key: 'disastersState',
  default: [],
});

export const resourcesState = atom<Resource[]>({
  key: 'resourcesState',
  default: [],
});

export const socialMediaState = atom<SocialMediaReport[]>({
    key: 'socialMediaState',
    default: [],
});

export const officialUpdatesState = atom<OfficialUpdate[]>({
    key: 'officialUpdatesState',
    default: [],
});

export const selectedDisasterState = atom<Disaster | null>({
    key: 'selectedDisasterState',
    default: null,
}); 