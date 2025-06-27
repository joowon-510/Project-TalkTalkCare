import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../../services/authService';
import '../../styles/components/Login.css';
import { useAuth } from '../../contexts/AuthContext';
import { useWebSocket } from '../../contexts/WebSocketContext';
import CustomModal from '../../components/CustomModal';

const Login: React.FC = () => {
  const navigate = useNavigate();
  const { setIsLoggedIn: setAuthLoggedIn, setUserName } = useAuth();
  const { setIsLoggedIn: setWsLoggedIn } = useWebSocket();
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [modalMessage, setModalMessage] = useState<string>('');
  const [modalTitle, setModalTitle] = useState<string>('알림');
  const [formData, setFormData] = useState({
    userLoginId: '',
    password: '',
    autoLogin: false
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
      const response = await authService.login(formData);
      
      if (response.result.msg === 'success') {
        localStorage.setItem('userId', response.body.userId);
        localStorage.setItem('name', response.body.username);
        localStorage.setItem('profile-image', response.body.s3Filename);
        localStorage.setItem('token', response.body.token);
  
        setAuthLoggedIn(true);
        setUserName(response.body.username);
        setWsLoggedIn(true);

        navigate('/', { replace: true });
        
      } else {
        setModalMessage(response.result.msg || '로그인에 실패했습니다.');
        setIsModalOpen(true);
      }
  };

  const handleUnsupportedFeature = (featureName: string) => {
    setModalTitle('서비스 안내');
    setModalMessage(`아직 지원되지 않는 서비스입니다.`);
    setIsModalOpen(true);
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <form onSubmit={handleLogin}>
          <div className="input-group">
            <input
              type="text"
              name="userLoginId"
              value={formData.userLoginId}
              onChange={handleChange}
              placeholder="아이디"
            />
          </div>

          <div className="input-group">
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="비밀번호"
            />
          </div>

          <div className="auto-login">
            <label>
              <input
                type="checkbox"
                name="autoLogin"
                checked={formData.autoLogin}
                onChange={handleChange}
              />
              <span>자동 로그인</span>
            </label>
          </div>

          <button 
            type="submit" 
            className="login-button"
            disabled={!formData.userLoginId || !formData.password}
          >
            로그인
          </button>
          <CustomModal
              title="알림"
              message={modalMessage}
              isOpen={isModalOpen}
              onClose={() => setIsModalOpen(false)}
            />
        </form>

        <div className="bottom-links">
          <span onClick={() => handleUnsupportedFeature('')}>아이디 찾기</span>
          <span className="divider">|</span>
          <span onClick={() => handleUnsupportedFeature('')}>비밀번호 찾기</span>
          <span className="divider">|</span>
          <span onClick={() => navigate('/sign-up')}>회원가입</span>
        </div>
      </div>
    </div>
  );
};

export default Login;
