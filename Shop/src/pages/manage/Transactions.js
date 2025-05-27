import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function Transactions() {
  const [cryptoTransactions, setCryptoTransactions] = useState([]);
  const [fiatTransactions, setFiatTransactions] = useState([]);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const API_URL = process.env.REACT_APP_API_URL;

  useEffect(() => {
    if (!token) {
      navigate("/");
    } else {
      fetchCryptoTransactions();
      fetchFiatTransactions();
    }
  }, [navigate, token]);

  const fetchCryptoTransactions = () => {
    fetch(`${API_URL}/api/details/all/crypto-transactions`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
        .then((res) => res.json())
        .then(setCryptoTransactions)
        .catch(() => setMessage("Failed to load crypto transactions"));
  };

  const fetchFiatTransactions = () => {
    fetch(`${API_URL}/api/details/all/fiat-transactions`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
    })
        .then((res) => res.json())
        .then(setFiatTransactions)
        .catch(() => setMessage("Failed to load fiat transactions"));
  };

  return (
      <main className="transaction-panel">
        <div className="transaction-container">
          <h1 className="transaction-title">All Transactions</h1>
          {message && <p className="error-message">{message}</p>}

          {/* === Crypto Transactions === */}
          <div className="transaction-section">
            <h2>Crypto Transactions</h2>
            {cryptoTransactions.length > 0 ? (
                <div className="transaction-scroll">
                  <table className="transaction-table">
                    <thead>
                    <tr>
                      <th>User ID</th>
                      <th>Invoice ID</th>
                      <th>Amount</th>
                      <th>Currency</th>
                      <th>Crypto</th>
                      <th>Status</th>
                      <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    {cryptoTransactions.map((t) => (
                        <tr key={t.invoiceId}>
                          <td>{t.userId}</td>
                          <td>{t.invoiceId}</td>
                          <td>${parseFloat(t.amount).toFixed(2)}</td>
                          <td>{t.currency}</td>
                          <td>{t.cryptocurrency}</td>
                          <td>{t.status}</td>
                          <td>{new Date(t.createdAt).toLocaleString()}</td>
                        </tr>
                    ))}
                    </tbody>
                  </table>
                </div>
            ) : (
                <p>No crypto transactions found.</p>
            )}
          </div>

          {/* === Fiat Transactions === */}
          <div className="transaction-section" style={{ marginTop: "40px" }}>
            <h2>Fiat Transactions</h2>
            {fiatTransactions.length > 0 ? (
                <div className="transaction-scroll">
                  <table className="transaction-table">
                    <thead>
                    <tr>
                      <th>User ID</th>
                      <th>Invoice ID</th>
                      <th>Amount</th>
                      <th>Currency</th>
                      <th>Status</th>
                      <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    {fiatTransactions.map((t) => (
                        <tr key={t.invoiceId}>
                          <td>{t.userId}</td>
                          <td>{t.invoiceId}</td>
                          <td>${parseFloat(t.amount).toFixed(2)}</td>
                          <td>{t.currency}</td>
                          <td>{t.status}</td>
                          <td>{new Date(t.createdAt).toLocaleString()}</td>
                        </tr>
                    ))}
                    </tbody>
                  </table>
                </div>
            ) : (
                <p>No fiat transactions found.</p>
            )}
          </div>

          <div className="transaction-actions">
            <button className="btn btn-back" onClick={() => navigate("/admin")}>
              Back to Admin Panel
            </button>
          </div>
        </div>
      </main>
  );
}

export default Transactions;
