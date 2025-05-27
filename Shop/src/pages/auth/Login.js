import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Modal = ({ isOpen, onClose, children }) => {
  if (!isOpen) return null;



  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="close-button" onClick={onClose}>
          ✖
        </button>
        {children}
      </div>
    </div>
  );
};

function Login() {
  const [message, setMessage] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [email, setEmail] = useState('');
  const navigate = useNavigate();
  const API_URL = process.env.REACT_APP_API_URL;
  console.log(API_URL)



  const handleSendEmail = async () => {
    if (!email) {
      setError('Email is required');
      return;
    }

    try {
      const response = await fetch(`${API_URL}/api/auth/forgot-password`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email }),
      });

      if (response.ok) {
        setMessage('Email sent successfully!');
        setError('');
      } else {
        const data = await response.text(); // Бек повертає просто текст
        setError(data || 'Failed to send email');
      }


    } catch (err) {
      clearMessageAfterTimeout(setError);
      setError('Server error. Please try again later.');
    }
  };
  const clearMessageAfterTimeout = (setMessageFunction) => {
    setTimeout(() => {
      setMessage("");
    }, 3000);
  };

  useEffect(() => {
    document.body.classList.add('auth-page');

    return () => {
      document.body.classList.remove('auth-page');
    };
  }, []);//d

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      localStorage.clear();
      const response = await fetch(`${API_URL}/api/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json(); // Отримуємо JSON-відповідь

      if (response.ok) {

        localStorage.setItem('token', data.token);

        const decodedToken = JSON.parse(atob(data.token.split(".")[1]));
        localStorage.setItem("token", data.token); // сам токен
        localStorage.setItem("userId", decodedToken.sub); // userId
        localStorage.setItem("email", decodedToken.email); // email
        localStorage.setItem("role", decodedToken.role); // role


        navigate('/logistics');
      } else {
        // Обробка помилок (отримуємо повідомлення з сервера)
        switch (response.status) {
            case 400:
                setError(data.message || "Bad request. Please check your input.");
                break;
            case 401:
                setError(data.message || "Incorrect username or password.");
                break;
            case 403:
                setError(data.message || "You do not have permission to access this resource.");
                break;
            case 404:
                setError(data.message || "User not found. Please check your credentials.");
                break;
            case 500:
                setError(data.message || "Internal server error. Please try again later.");
                break;
            default:
                setError("An unknown error occurred. Please try again.");
          }
     }
      } catch (error) {
          setError("Network error. Please check your internet connection.");
      }
  };

  return (
    <main className="login-main">
      <div className="login-container">
        <h1 className="title">Sign in</h1>

        <form className="form" onSubmit={handleSubmit} autoComplete="on">
          <div className="form-field">
            <input
              type="text"
              placeholder="Email"
              id="login-email"
              name="email"
              autoComplete="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="form-field">
            <input
              type="password"
              placeholder="Password"
              id="login-password"
              name="password"
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          {error && <p className="error-message">{error}</p>}

          <div className="form-field">
            <button className="btn btn-submit" type="submit">
              Log In
            </button>
            <div className='login-registration-container'>
              Don't have an account?
              <Link to="/registration" className="login-registration"> Sign up</Link>
            </div>
            <div className='forgotPassword-container'>
              <span style={{display: 'block', textAlign: 'center'}}>Forgot password?</span>
              <button
                type="button"
                className="forgotPassword-link"
                onClick={() => setIsModalOpen(true)}
              >
                Click here
              </button>
            </div>
          </div>
        </form>
      </div>

      <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
        <h2>Reset Password</h2>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="Enter your email"
          className="modal-input"
        />
        {error && <p className="modal-error">{error}</p>}
        {message && <p className="modal-success">{message}</p>}
        <button className="modal-button" onClick={handleSendEmail}>
          Reset Password
        </button>
      </Modal>
    </main>
  );
}

export default Login;
