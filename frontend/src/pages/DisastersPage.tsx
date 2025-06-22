import React from 'react';
import Layout from '../components/layout/Layout';
import DisasterList from '../components/disaster/DisasterList';
import DisasterForm from '../components/disaster/DisasterForm';

const DisastersPage: React.FC = () => {
  return (
    <Layout>
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2">
          <DisasterList />
        </div>
        <div>
          <DisasterForm />
        </div>
      </div>
    </Layout>
  );
};

export default DisastersPage; 