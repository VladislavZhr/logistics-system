import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { MdDeleteForever } from "react-icons/md";

function AdminPanel() {
    const [users, setUsers] = useState([]);
    const [message, setMessage] = useState("");
    const [selectedUserId, setSelectedUserId] = useState(null);
    const [newRole, setNewRole] = useState("USER");

    const navigate = useNavigate();
    const token = localStorage.getItem("token");
    const API_URL = process.env.REACT_APP_API_URL;

    useEffect(() => {
        fetch(`${API_URL}/api/gateway-admin/users-with-balance`, {
            method: "GET",
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(res => res.json())
            .then(data => setUsers(data))
            .catch(err => {
                console.error("Error loading users", err);
                setMessage("Failed to load users.");
            });
    }, [API_URL, token]);

    const handleDeleteUser = (userId) => {
        fetch(`${API_URL}/api/admin/delete?userId=${userId}`, {
            method: "POST",
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(res => res.text())
            .then(() => {
                setUsers(users.filter(u => u.id !== userId));
                setMessage("User deleted successfully.");
            })
            .catch(err => {
                console.error("Error deleting user", err);
                setMessage("Failed to delete user.");
            });
    };

    const handleChangeRole = () => {
        if (!selectedUserId || !newRole) return;

        fetch(`${API_URL}/api/admin/change-role?userId=${selectedUserId}&role=${newRole}`, {
            method: "POST",
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(res => res.text())
            .then(() => {
                setUsers(users.map(u =>
                    u.id === selectedUserId ? { ...u, role: newRole } : u
                ));
                setMessage("User role changed successfully.");
            })
            .catch(err => {
                console.error("Error changing role", err);
                setMessage("Failed to change role.");
            });
    };

    return (
        <main className="admin-panel">
            <div className="admin-container">
                <h1 className="admin-title">Admin Panel</h1>

                <div className="admin-header">
                    <p
                        style={{
                            fontSize: "16px",
                            fontWeight: "600",
                            marginBottom: "6px",
                            display: "inline-block",
                            color: "#333",
                            fontFamily: "Poppins, sans-serif",
                            letterSpacing: "0.5px"
                        }}>Manage users and system settings</p>
                    <div>
                        <button className="logout-button" onClick={() => navigate("/")}>Log Out</button>
                        <button className="logout-button" onClick={() => navigate("/transactions")}>
                            Check Transactions
                        </button>
                    </div>
                </div>

                {message && <p className="success-message">{message}</p>}

                {/* Панель для зміни ролі */}
                <div style={{marginBottom: "20px", display: "flex", alignItems: "center", gap: "10px"}}>
                    <select
                        value={newRole}
                        onChange={(e) => setNewRole(e.target.value)}
                        style={{
                            width: "100px",
                            padding: "10px 12px",
                            borderRadius: "8px",
                            border: "1px solid #ccc",
                            backgroundColor: "#f9f9f9",
                            fontSize: "14px",
                            fontFamily: "Poppins, sans-serif",
                            cursor: "pointer",
                            boxShadow: "0 2px 5px rgba(0,0,0,0.05)"
                        }}
                    >
                        <option value="USER">USER</option>
                        <option value="MANAGER">MANAGER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                    <button className="logout-button" onClick={handleChangeRole} disabled={!selectedUserId}>
                        Change Role
                    </button>
                </div>

                {/* Таблиця користувачів */}
                <div className="user-list">
                    <h2>Registered Users</h2>
                    {users.length > 0 ? (
                        <table className="user-table">
                            <thead>
                            <tr>
                                <th>Select</th>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Balance</th>
                                <th>Delete</th>
                            </tr>
                            </thead>
                            <tbody>
                            {users.map(user => (
                                <tr key={user.id}>
                                    <td>
                                        <input
                                            type="radio"
                                            name="selectedUser"
                                            onChange={() => setSelectedUserId(user.id)}
                                            checked={selectedUserId === user.id}
                                        />
                                    </td>
                                    <td>{user.id}</td>
                                    <td>{user.username}</td>
                                    <td>{user.email}</td>
                                    <td>{user.role}</td>
                                    <td>${parseFloat(user.balance).toFixed(2)}</td>

                                    <td>
                                        <button onClick={() => handleDeleteUser(user.id)} className="remove-button">
                                            <MdDeleteForever style={{fontSize: "20px"}}/>
                                        </button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    ) : (
                        <p>No users found.</p>
                    )}
                </div>
            </div>
        </main>
    );
}

export default AdminPanel;
