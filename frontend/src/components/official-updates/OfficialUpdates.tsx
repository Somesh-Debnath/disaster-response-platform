import React, { useEffect } from 'react';
import { useRecoilValue, useRecoilState } from 'recoil';
import { selectedDisasterState, officialUpdatesState } from '../../state/atoms';
import { getOfficialUpdates } from '../../services/api';

const OfficialUpdates: React.FC = () => {
  const selectedDisaster = useRecoilValue(selectedDisasterState);
  const [updates, setUpdates] = useRecoilState(officialUpdatesState);

  useEffect(() => {
    if (selectedDisaster) {
      const fetchUpdates = async () => {
        try {
          const data = await getOfficialUpdates(selectedDisaster.id);
          setUpdates(data);
        } catch (error) {
          console.error("Error fetching official updates:", error);
        }
      };
      fetchUpdates();
    }
  }, [selectedDisaster, setUpdates]);

  return (
    <div className="bg-white p-4 rounded-lg shadow-md">
      <h2 className="text-xl font-bold mb-4">Official Updates</h2>
      <div>
        {updates.length > 0 ? updates.map((update) => (
          <div key={update.id} className="border-b last:border-b-0 py-2">
            <p className="font-bold">{update.source} - <a href={update.url} target="_blank" rel="noopener noreferrer" className="text-blue-500 hover:underline">{update.title}</a></p>
            <p>{update.content}</p>
            <p className="text-sm text-gray-500">{new Date(update.publishedAt).toLocaleString()}</p>
          </div>
        )) : <p>No official updates for this disaster.</p>}
      </div>
    </div>
  );
};

export default OfficialUpdates; 