import React from 'react';

const Header: React.FC = () => {
  return (
    <header className="bg-gray-800 text-white p-4">
      <div className="container mx-auto flex justify-between items-center">
        <h1 className="text-2xl font-bold">Disaster Response Platform</h1>
        <nav>
          <a href="/" className="px-4">Home</a>
          <a href="/disasters" className="px-4">Disasters</a>
          <a href="/map" className="px-4">Map</a>
        </nav>
      </div>
    </header>
  );
};

export default Header; 