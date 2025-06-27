import React from 'react';
import WsGameListPage from './GamePages/ws/WsGameListPage';
import '../styles/components/VideoCall.css'; // VideoCall에서 사용하는 css를 불러옴

const Temp: React.FC = () => {
  return (
    <div className="videocall-content" style={{ display: 'flex' }}>
      <div className="video-section" style={{ width: '50%', background: '#fff' }}>
        {/* 왼쪽 영역은 임시 콘텐츠로 채워서 레이아웃을 확인 */}
        <p>임시 비디오 섹션</p>
      </div>
      <div className="game-section" style={{ width: '50%', background: '#ffffff', overflowY: 'auto' }}>
        <WsGameListPage />
      </div>
    </div>
  );
};

export default Temp;
