import React, { useEffect } from 'react';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { disastersState, selectedDisasterState } from '../../state/atoms';
import { getDisasters } from '../../services/api';

const DisasterList: React.FC = () => {
  const [disasters, setDisasters] = useRecoilState(disastersState);
  const setSelectedDisaster = useSetRecoilState(selectedDisasterState);

  useEffect(() => {
    // Fetch disasters when the component mounts
    const fetchDisasters = async () => {
      try {
        const data = await getDisasters();
        setDisasters(data);
        // Select the first disaster by default
        if (data.length > 0) {
          setSelectedDisaster(data[0]);
        }
      } catch (error) {
        console.error("Error fetching disasters:", error);
      }
    };

    fetchDisasters();
  }, [setDisasters, setSelectedDisaster]);

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Disasters</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {disasters.map((disaster) => (
          <div 
            key={disaster.id} 
            className="bg-white p-4 rounded-lg shadow cursor-pointer hover:shadow-lg"
            onClick={() => setSelectedDisaster(disaster)}
          >
            <h3 className="text-xl font-bold">{disaster.title}</h3>
            <p className="text-gray-600">{disaster.locationName}</p>
            <p className="mt-2 text-sm">{disaster.description}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default DisasterList; 