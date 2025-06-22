import React, { useState } from 'react';
import { useSetRecoilState } from 'recoil';
import { disastersState } from '../../state/atoms';
import { createDisaster } from '../../services/api';

const DisasterForm: React.FC = () => {
  const [title, setTitle] = useState('');
  const [locationName, setLocationName] = useState('');
  const [description, setDescription] = useState('');
  const [tags, setTags] = useState('');
  const setDisasters = useSetRecoilState(disastersState);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const newDisaster = await createDisaster({
        title,
        locationName,
        description,
        tags: tags.split(',').map(tag => tag.trim()),
        // These fields will be populated by the backend
        latitude: 0, 
        longitude: 0,
        ownerId: 'frontend-user'
      });
      setDisasters(oldDisasters => [newDisaster, ...oldDisasters]);
      // Reset form
      setTitle('');
      setLocationName('');
      setDescription('');
      setTags('');
    } catch (error) {
      console.error("Error creating disaster:", error);
    }
  };

  return (
    <div className="bg-white p-6 rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-4">Create Disaster</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="title">
            Title
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="title"
            type="text"
            placeholder="Disaster Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="locationName">
            Location Name
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="locationName"
            type="text"
            placeholder="e.g., Manhattan, NYC"
            value={locationName}
            onChange={(e) => setLocationName(e.target.value)}
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="description">
            Description
          </label>
          <textarea
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="description"
            placeholder="A brief description of the disaster"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </div>
        <div className="mb-6">
          <label className="block text-gray-700 text-sm font-bold mb-2" htmlFor="tags">
            Tags (comma-separated)
          </label>
          <input
            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="tags"
            type="text"
            placeholder="e.g., flood, urgent"
            value={tags}
            onChange={(e) => setTags(e.target.value)}
          />
        </div>
        <div className="flex items-center justify-between">
          <button
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
            type="submit"
          >
            Create Disaster
          </button>
        </div>
      </form>
    </div>
  );
};

export default DisasterForm; 