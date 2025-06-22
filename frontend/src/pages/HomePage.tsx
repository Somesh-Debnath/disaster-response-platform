import React from 'react';
import Layout from '../components/layout/Layout';
import SocialMediaFeed from '../components/social-media/SocialMediaFeed';
import OfficialUpdates from '../components/official-updates/OfficialUpdates';
import ResourceList from '../components/resource/ResourceList';

const HomePage: React.FC = () => {
  return (
    <Layout>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <div className="lg:col-span-2">
          <h2 className="text-2xl font-bold mb-4">Recent Activity</h2>
          <SocialMediaFeed />
        </div>
        <div>
          <h2 className="text-2xl font-bold mb-4">Official Updates</h2>
          <OfficialUpdates />
        </div>
        <div className="col-span-1 md:col-span-2 lg:col-span-3">
          <h2 className="text-2xl font-bold mb-4">Nearby Resources</h2>
          <ResourceList />
        </div>
      </div>
    </Layout>
  );
};

export default HomePage; 