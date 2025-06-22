import React, { useEffect } from 'react';
import { useRecoilValue, useRecoilState } from 'recoil';
import { selectedDisasterState, resourcesState } from '../../state/atoms';
import { getResourcesForDisaster } from '../../services/api';

const ResourceList: React.FC = () => {
  const selectedDisaster = useRecoilValue(selectedDisasterState);
  const [resources, setResources] = useRecoilState(resourcesState);

  useEffect(() => {
    if (selectedDisaster) {
      const fetchResources = async () => {
        try {
          const data = await getResourcesForDisaster(selectedDisaster.id);
          setResources(data);
        } catch (error) {
          console.error("Error fetching resources:", error);
        }
      };
      fetchResources();
    }
  }, [selectedDisaster, setResources]);

  return (
    <div className="bg-white p-4 rounded-lg shadow-md">
      <h2 className="text-xl font-bold mb-4">Available Resources</h2>
      <div>
        {resources.length > 0 ? resources.map((resource) => (
          <div key={resource.id} className="border-b last:border-b-0 py-2">
            <p className="font-bold">{resource.name} ({resource.type})</p>
            <p>{resource.locationName}</p>
          </div>
        )) : <p>No resources listed for this disaster.</p>}
      </div>
    </div>
  );
};

export default ResourceList; 