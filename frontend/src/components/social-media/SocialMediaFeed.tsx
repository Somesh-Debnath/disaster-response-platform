import React, { useEffect } from 'react';
import { useRecoilValue, useRecoilState } from 'recoil';
import { selectedDisasterState, socialMediaState } from '../../state/atoms';
import { getSocialMediaReports } from '../../services/api';

const SocialMediaFeed: React.FC = () => {
  const selectedDisaster = useRecoilValue(selectedDisasterState);
  const [reports, setReports] = useRecoilState(socialMediaState);

  useEffect(() => {
    if (selectedDisaster) {
      const fetchReports = async () => {
        try {
          const data = await getSocialMediaReports(selectedDisaster.id);
          setReports(data);
        } catch (error) {
          console.error("Error fetching social media reports:", error);
        }
      };
      fetchReports();
    }
  }, [selectedDisaster, setReports]);

  return (
    <div className="bg-white p-4 rounded-lg shadow-md">
      <h2 className="text-xl font-bold mb-4">Social Media Feed</h2>
      <div>
        {reports.length > 0 ? reports.map((report) => (
          <div key={report.id} className="border-b last:border-b-0 py-2">
            <p className="font-bold">{report.user}</p>
            <p>{report.post}</p>
            <p className="text-sm text-gray-500">{new Date(report.timestamp).toLocaleString()}</p>
          </div>
        )) : <p>No social media reports for this disaster.</p>}
      </div>
    </div>
  );
};

export default SocialMediaFeed; 