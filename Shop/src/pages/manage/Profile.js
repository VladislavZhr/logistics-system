import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

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

function Profile() {
  const [transactions, setTransactions] = useState([]);
  const navigate = useNavigate();
  const [userData, setUserData] = useState(null);
  const [message, setMessage] = useState("");
  const [oldPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [passwordChangeMessage, setPasswordChangeMessage] = useState("");
  const token = localStorage.getItem("token");
  const [error, setError] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [amount, setAmount] = useState("");
  const userId = localStorage.getItem("userId");
  const email = localStorage.getItem("email");
  const username = localStorage.getItem("email");
  const [balance, setBalance] = useState(null);
  const [paymentType, setPaymentType] = useState("crypto"); // або "fiat"
  const [selectedCurrency, setSelectedCurrency] = useState("USD");


  const [cryptoTransactions, setCryptoTransactions] = useState([]);
  const [fiatTransactions, setFiatTransactions] = useState([]);

  const API_URL = process.env.REACT_APP_API_URL

  useEffect(() => {
    if (!token) {
      navigate("/");
    } else {
      getUserData();
      getBalance();
      fetchCryptoTransactions();
      fetchFiatTransactions();
    }
  }, [navigate, token]);


  function fetchCryptoTransactions() {
    fetch(`${API_URL}/api/details/crypto-transactions`, {
      method: "GET",
      headers: {
        "X-User-Id": userId,
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
        .then((response) => {
          if (!response.ok) {
            return response.json().then((error) => {
              throw new Error(error.message || "Failed to fetch crypto transactions");
            });
          }
          return response.json();
        })
        .then((data) => {
          setCryptoTransactions(data);
        })
        .catch((error) => {
          console.error("Crypto transactions error:", error);
        });
  }

  function fetchFiatTransactions() {
    fetch(`${API_URL}/api/details/fiat-transactions`, {
      method: "GET",
      headers: {
        "X-User-Id": userId,
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
        .then((response) => {
          if (!response.ok) {
            return response.json().then((error) => {
              throw new Error(error.message || "Failed to fetch fiat transactions");
            });
          }
          return response.json();
        })
        .then((data) => {
          setFiatTransactions(data);
        })
        .catch((error) => {
          console.error("Fiat transactions error:", error);
        });
  }


  function getBalance() {
    fetch(`${API_URL}/api/details/balance?userId=${encodeURIComponent(userId)}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
        .then((response) => {
          if (!response.ok) {
            return response.text().then(text => {
              throw new Error(text || "Failed to fetch balance");
            });
          }
          return response.json();
        })
        .then((data) => {
          setBalance(data);
        })
        .catch((error) => {
          console.error("Error fetching balance:", error);
          setMessage("Failed to load balance.");
        });
  }

  function getUserData() {
    fetch(`${API_URL}/api/auth/me?email=${encodeURIComponent(email)}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
        .then((response) => {
          if (!response.ok) {
            return response.json().then((error) => {
              throw new Error(error.error || "Failed to fetch user profile");
            });
          }
          return response.json();
        })
        .then((data) => {
          setUserData(data);
        })
        .catch((error) => {
          console.error("Error fetching user profile:", error);
          setMessage("Failed to load user profile. Please try again.");
        });
  }


  function handleCreatePayment() {
    const numericAmount = parseFloat(amount);

    if (!numericAmount || numericAmount <= 0) {
      setError("Please enter a valid amount.");
      clearMessageAfterTimeout(setError);
      return;
    }

    const endpoint =
        paymentType === "crypto"
            ? `${API_URL}/api/crypto/create-payment`
            : `${API_URL}/api/fiat/create-payment`;

    fetch(endpoint, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
        "X-User-Id": userId,
      },
      body: JSON.stringify({
        amount: numericAmount.toString(),
        currency: selectedCurrency,
      }),
    })
        .then((response) => {
          const contentType = response.headers.get("Content-Type");

          if (!response.ok) {
            if (contentType && contentType.includes("application/json")) {
              return response.json().then((error) => {
                throw new Error(error.error || "Failed to create payment");
              });
            } else {
              throw new Error("Failed to create payment. Server returned non-JSON response.");
            }
          }

          return contentType && contentType.includes("application/json")
              ? response.json()
              : response.text();
        })
        .then((data) => {
          const link = typeof data === "string" ? data : data.paymentUrl;
          if (link && link.startsWith("http")) {
            window.location.href = link;
          } else {
            throw new Error("Unexpected response format.");
          }
        })
        .catch((error) => {
          console.error("Error creating payment:", error);
          setError(error.message || "Error creating payment. Please try again.");
          clearMessageAfterTimeout(setError);
        });
  }

  const handlePasswordChange = async () => {
    if (newPassword !== confirmPassword) {
      setPasswordChangeMessage("New passwords do not match.");
      clearMessageAfterTimeout(setPasswordChangeMessage);
      return;
    }

    const email = localStorage.getItem("email");

    try {
      const response = await fetch(`${API_URL}/api/auth/change-password`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
          oldPassword,
          newPassword
        }),
      });

      if (response.ok) {
        setPasswordChangeMessage("Password changed successfully!");
        clearMessageAfterTimeout(setPasswordChangeMessage);
      } else {
        const error = await response.text();
        setMessage(error || 'Error: Invalid request or server issue.');
        clearMessageAfterTimeout(setMessage);
      }
    } catch (error) {
      setMessage('Server error. Please try again later.');
      clearMessageAfterTimeout(setMessage);
    }
  };


  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  const clearMessageAfterTimeout = (setMessageFunction) => {
    setTimeout(() => {
      setMessageFunction("");
    }, 3000);
  };

  return (
      <div className="profile-container">
        <div className="profile-header">
          <div className="profile-avatar">A</div>
          <div className="profile-info">
            <h2>{userData ? userData.username : "Loading..."}</h2>
            <p>Email: {userData ? userData.email : "Loading..."}</p>
            <p>Balance: {balance !== null ? `$${parseFloat(balance).toFixed(2)}` : "Loading..."}</p>
          </div>
          <div className="addBalance-container">
            <button className="addBalance" onClick={() => {
              setPaymentType("crypto");
              setIsModalOpen(true);
            }}>
              Add Crypto Balance
            </button>

            <button className="addBalance" onClick={() => {
              setPaymentType("fiat");
              setIsModalOpen(true);
            }}>
              Add Fiat Balance
            </button>

          </div>
        </div>

        <div className="profile-section">
          <h3>Update Password</h3>
          <table className="password-table">
            <tbody>
            <tr>
              <td>Current Password:</td>
              <td>
                <input
                    type="password"
                    value={oldPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                    placeholder="Enter current password"
                />
              </td>
            </tr>
            <tr>
              <td>New Password:</td>
              <td>
                <input
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    placeholder="Enter new password"
                />
              </td>
            </tr>
            <tr>
              <td>Confirm Password:</td>
              <td>
                <input
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    placeholder="Confirm new password"
                />
              </td>
            </tr>
            </tbody>
          </table>
          <div>
            <button type="button" onClick={handlePasswordChange}>
              Change Password
            </button>
          </div>

          {passwordChangeMessage && (
              <p className="message">{passwordChangeMessage}</p>
          )}
          {message && <p className="message">{message}</p>}
        </div>

        <div className="profile-section order-history">
          <h3>Fiat Transaction History</h3>
          {fiatTransactions.length > 0 ? (
                  <div className="transaction-scroll">
                    <table className="transaction-table">
                      <thead>
                      <tr>
                        <th>Invoice ID</th>
                        <th>Amount</th>
                        <th>Currency</th>
                        <th>Date</th>
                        <th>Status</th>
                      </tr>
                      </thead>
                      <tbody>
                      {fiatTransactions.map((t) => (
                          <tr key={t.invoiceId}>
                            <td>{t.invoiceId}</td>
                            <td>${parseFloat(t.amount).toFixed(2)}</td>
                            <td>{t.currency}</td>
                            <td>{new Date(t.createdAt).toLocaleString()}</td>
                            <td>{t.status}</td>
                          </tr>
                      ))}
                      </tbody>
                    </table>
                  </div>
                    ) : (
                    <p>No fiat transactions found.</p>
                    )}
                  </div>

              <div className="profile-section order-history">
          <h3>Crypto Transaction History</h3>
          {cryptoTransactions.length > 0 ? (
              <table className="transaction-table">
                <thead>
                <tr>
                  <th>Invoice ID</th>
                  <th>Amount</th>
                  <th>Currency</th>
                  <th>Crypto</th>
                  <th>Date</th>
                  <th>Status</th>
                </tr>
                </thead>
                <tbody>
                {cryptoTransactions.map((t) => (
                    <tr key={t.invoiceId}>
                      <td>{t.invoiceId}</td>
                      <td>${parseFloat(t.amount).toFixed(2)}</td>
                      <td>{t.currency}</td>
                      <td>{t.cryptocurrency}</td>
                      <td>{new Date(t.createdAt).toLocaleString()}</td>
                      <td>{t.status}</td>
                    </tr>
                ))}
                </tbody>
              </table>
          ) : (
              <p>No crypto transactions found.</p>
          )}
        </div>

        <button className="logout-button" onClick={handleLogout}>
          Logout
        </button>
        <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
          <h2>Increase your balance</h2>
          <p>Selected method: <strong>{paymentType.toUpperCase()}</strong></p>
          <input
              type="number"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              placeholder="Enter your amount"
              className="modal-input"
          />

          {/*<select*/}
          {/*    value={selectedCurrency}*/}
          {/*    onChange={(e) => setSelectedCurrency(e.target.value)}*/}
          {/*    className="modal-input"*/}
          {/*>*/}
          {/*  <option value="USD">USD</option>*/}
          {/*  <option value="EUR">EUR</option>*/}
          {/*</select>*/}

          {error && <p className="modal-error">{error}</p>}
          {message && <p className="modal-success">{message}</p>}
          <button className="modal-button" onClick={handleCreatePayment}>
            Create Payment
          </button>
        </Modal>
      </div>


  );
}

export default Profile;
