import React, { useState, useEffect } from 'react';
import { Link, useLocation } from 'react-router-dom';

function Menu() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const location = useLocation();
  const role = localStorage.getItem("role").slice(0);


  const toggleMenu = () => setIsMenuOpen(!isMenuOpen);

  useEffect(() => setIsMenuOpen(false), [location.pathname]);
  const isAdmin = role === 'ADMIN';
  const isManager = role === 'MANAGER' || role === 'ADMIN';


  return (
    <div className={`menu-container ${isMenuOpen ? 'open' : ''}`}>
      <div className="menu-trigger" onClick={toggleMenu}>
        <div className="bars">
          <span></span>
          <span></span>
          <span></span>
        </div>
        <p>MENU</p>
      </div>

      <nav className={`menu ${isMenuOpen ? 'open' : ''}`}>
        <ul>
          <li><Link to="/logistics">Logistics</Link></li> {/* 🆕 Додали сюди */}
          <li><Link to="/profile">Profile</Link></li>
          <li><Link to="/orders">Orders</Link></li>
          {isManager && <li><Link to="/manager">Manager Panel</Link></li>}
          {isAdmin && <li><Link to="/admin">Admin Panel</Link></li>} {/* Лише для адмінів */}
        </ul>
      </nav>
    </div>
  );
}

export default Menu;
