import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/components/test.css';

const Test: React.FC = () => {
  return (
    <div className="test-container">
      <div className='test-page'>
        <div className="title-section">
          <p className='test-title font-bold'>치매 진단 테스트</p>
        </div>

        <div className="buttons-container">
          <Link to="/smcq" className="test-button">
            <p className='test-option'>이용자용 테스트</p>
            <p><br />SMCQ</p>
          </Link>

          <Link to="/sdq" className="test-button">
            <p className='test-option'>보호자용 테스트</p>
            <p><br />SDQ</p>
          </Link>
        </div>    
      </div>
    </div>
  );
};

export default Test;