import React, { useState } from 'react';
import Map, { Marker, Popup } from 'react-map-gl';
import { useRecoilValue } from 'recoil';
import { disastersState, resourcesState, Disaster, Resource } from '../../state/atoms';

// IMPORTANT: Replace with your own Mapbox token
const MAPBOX_TOKEN = 'pk.eyJ1IjoiZGVid2ViZ3AiLCJhIjoiY2x4NW50YXd0MGh2aTJrcWludm5sMnluaSJ9.7n9fK-jHqXynB4lPGA0p4Q';

const MapView: React.FC = () => {
  const disasters = useRecoilValue(disastersState);
  const resources = useRecoilValue(resourcesState);
  const [selected, setSelected] = useState<Disaster | Resource | null>(null);

  return (
    <div className="bg-white rounded-lg shadow-md h-full w-full">
      <Map
        initialViewState={{
          longitude: -74.0060,
          latitude: 40.7128,
          zoom: 10
        }}
        style={{width: '100%', height: '600px'}}
        mapStyle="mapbox://styles/mapbox/dark-v10"
        mapboxAccessToken={MAPBOX_TOKEN}
      >
        {/* Disaster Markers */}
        {disasters.map(disaster => (
          <Marker key={`disaster-${disaster.id}`} longitude={disaster.longitude} latitude={disaster.latitude}>
            <button
              onClick={(e) => {
                e.preventDefault();
                setSelected(disaster);
              }}
              className="bg-red-500 w-4 h-4 rounded-full border-2 border-white"
            />
          </Marker>
        ))}

        {/* Resource Markers */}
        {resources.map(resource => (
          <Marker key={`resource-${resource.id}`} longitude={resource.longitude} latitude={resource.latitude}>
             <button
              onClick={(e) => {
                e.preventDefault();
                setSelected(resource);
              }}
              className="bg-green-500 w-3 h-3 rounded-full border-2 border-white"
            />
          </Marker>
        ))}

        {selected && (
          <Popup
            longitude={selected.longitude}
            latitude={selected.latitude}
            onClose={() => setSelected(null)}
            anchor="top"
          >
            <div>
              <h3 className="font-bold">{selected.title || selected.name}</h3>
              <p>{selected.locationName}</p>
              {'description' in selected && <p className="text-sm">{selected.description}</p>}
              {'type' in selected && <p className="text-sm">Type: {selected.type}</p>}
            </div>
          </Popup>
        )}
      </Map>
    </div>
  );
};

export default MapView; 