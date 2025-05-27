import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Login from './pages/auth/Login';
import Registration from './pages/auth/Registration';
import "./index.css";
import "./main-page.css";
import Profile from './pages/manage/Profile';
import ResetPassword from './pages/auth/PasswordReset';
import AdminPanel from './pages/manage/Admin';
import Orders from './pages/manage/Orders'
import ForgotPassword from './pages/auth/ForgotPassword'
import Transactions from './pages/manage/Transactions';
import ManagerPanel from './pages/manage/ManagerPanel';
import LogisticsPage from './pages/manage/LogisticsPage';



function App() {
  return (
    <Router>
      <Routes>
        {/* Публічні сторінки */}
        <Route path="/" element={<Login />} />
        <Route path="/registration" element={<Registration />} />
        <Route path="/passwordreset" element={<ResetPassword />} />
        <Route path="/forgotpassword" element={<ForgotPassword />} />

        {/* Сторінки з Layodut */}
        <Route path="/" element={<Layout />}>
          <Route path="profile" element={<Profile />} />
          <Route path="manager" element={<ManagerPanel />} />
          <Route path="orders" element={<Orders />} />
          <Route path="transactions" element={<Transactions />} />
          <Route path="admin"element={<AdminPanel /> } />
          <Route path="logistics" element={<LogisticsPage />} /> {/* ✅ Нова сторінка */}
        </Route>
      </Routes>
    </Router>

  );
}

export default App;
