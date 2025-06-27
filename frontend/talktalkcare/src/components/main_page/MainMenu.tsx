import { useState } from "react";
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Phone, GamepadIcon, FileText, User } from 'lucide-react';
import '../../styles/components/MenuItem.css';
import CustomModal from "../CustomModal";

const MainMenu: React.FC<{ isFriendListOpen: boolean }> = ({ isFriendListOpen }) => {
  const navigate = useNavigate();
  const userId = localStorage.getItem('userId');
  const [isModalOpen, setIsModalOpen] = useState(false);// 로그인 상태 확인

  const handleNavigation = (path: string, requiresAuth: boolean = false) => {
    if (requiresAuth && !userId) {
      setIsModalOpen(true);
      return;
    }
    navigate(path);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    navigate('/login');
  };
  return (
    <div className={`menu ${isFriendListOpen ? 'compressed' : ''}`}>
      <nav className={`menu-grid ${isFriendListOpen ? 'compressed-grid' : ''}`}>
        <div onClick={() => handleNavigation('/call', true)} className="menu-item">
          <div className="menu-item-icon">
            <Phone size={40} />
          </div>
          <p className="menu-item-text">화상통화</p>
        </div>

        <div onClick={() => handleNavigation('/game')} className="menu-item">
          <div className="menu-item-icon">
            <GamepadIcon size={40} />
          </div>
          <p className="menu-item-text">치매 예방 게임</p>
        </div>

        <div onClick={() => handleNavigation('/test')} className="menu-item">
          <div className="menu-item-icon">
            <FileText size={40} />
          </div>
          <p className="menu-item-text">치매 진단<br />테스트</p>
        </div>

        <div onClick={() => handleNavigation('/mypage', true)} className="menu-item">
          <div className="menu-item-icon">
            <User size={40} />
          </div>
          <p className="menu-item-text">마이 페이지</p>
        </div>
      </nav>

      <CustomModal
        title="알림"
        message="로그인이 필요한 서비스입니다."
        isOpen={isModalOpen}
        onClose={handleModalClose}
      />
    </div>
  );
};

export default MainMenu;