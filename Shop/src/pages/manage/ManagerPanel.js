import React, { useEffect, useState } from "react";

function ManagerPanel() {
    const [warehouses, setWarehouses] = useState([]);
    const [orders, setOrders] = useState([]);
    const [selectedOrderId, setSelectedOrderId] = useState(null);
    const [selectedStatus, setSelectedStatus] = useState("CREATED");
    const [deliveryTime, setDeliveryTime] = useState("");
    const token = localStorage.getItem("token");
    const API_URL = process.env.REACT_APP_API_URL;
    const userId = localStorage.getItem("userId");
    const [successMessage, setSuccessMessage] = useState("");

    const showSuccess = (msg) => {
        setSuccessMessage(msg);
        setTimeout(() => setSuccessMessage(""), 3000);
    };

    useEffect(() => {
        fetch(`${API_URL}/api/warehouse`, {
            method: "GET",
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(res => res.json())
            .then(data => setWarehouses(data))
            .catch(err => console.error("Warehouses load error:", err));
    }, []);

    useEffect(() => {
        fetch(`${API_URL}/api/order/get-all`, {
            method: "GET",
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(res => res.json())
            .then(data => setOrders(data))
            .catch(err => console.error("Orders load error:", err));
    }, []);

    const handleChangeStatus = () => {
        if (!selectedOrderId) return;
        fetch(`${API_URL}/api/order/change-status`, {
            method: "POST",
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                orderId: selectedOrderId,
                status: selectedStatus,
            }),
        })
            .then(res => res.text())
            .then(() => showSuccess("Status updated successfully"))
            .catch(err => console.error("Error updating status", err));
    };

    const handleChangeDeliveryTime = () => {
        if (!selectedOrderId || !deliveryTime) return;
        fetch(`${API_URL}/api/order/set-deliveryTime`, {
            method: "POST",
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                oderId: selectedOrderId,
                deliveryTime: new Date(deliveryTime),
            }),
        })
            .then(res => res.text())
            .then(() => showSuccess("Delivery time updated successfully"))
            .catch(err => console.error("Error updating delivery time", err));
    };

    const handleDeleteOrder = () => {
        if (!selectedOrderId) return;
        fetch(`${API_URL}/api/order?orderId=${selectedOrderId}`, {
            method: "DELETE",
            headers: { Authorization: `Bearer ${token}` },
        })
            .then(res => res.text())
            .then(() => {
                showSuccess("Order deleted successfully");
                setOrders(orders.filter(order => order.orderId !== selectedOrderId));
            })
            .catch(err => console.error("Error deleting order", err));
    };

    return (
        <div className="Orders-container">
            <div className="OrdersPage">

                {/* КНОПКИ НАД ТАБЛИЦЕЮ */}
                <div className="manager-controls">
                    <div>
                        <label
                            style={{
                                fontSize: "16px",
                                fontWeight: "600",
                                marginBottom: "6px",
                                display: "inline-block",
                                color: "#333",
                                fontFamily: "Poppins, sans-serif",
                                letterSpacing: "0.5px"
                            }}>Status:</label>
                        <select
                            value={selectedStatus}
                            onChange={(e) => setSelectedStatus(e.target.value)}
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
                            <option value="CREATED">CREATED</option>
                            <option value="PROCESSING">PROCESSING</option>
                            <option value="DELIVERED_TO_BASE">DELIVERED_TO_BASE</option>
                            <option value="DELIVERED_TO_CLIENT">DELIVERED_TO_CLIENT</option>
                        </select>
                        <button onClick={handleChangeStatus} className="profile-section-button">
                            Change Status
                        </button>
                    </div>

                    <div>
                        <label
                            style={{
                                fontSize: "16px",
                                fontWeight: "600",
                                marginBottom: "6px",
                                display: "inline-block",
                                color: "#333",
                                fontFamily: "Poppins, sans-serif",
                                letterSpacing: "0.5px"
                            }}
                        >
                             Delivery Time:
                        </label>

                        <input
                            type="datetime-local"
                            value={deliveryTime}
                            onChange={(e) => setDeliveryTime(e.target.value)}
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
                        />
                        <button onClick={handleChangeDeliveryTime} className="profile-section-button">
                            Set Delivery Time
                        </button>
                    </div>

                    <div>
                        <button onClick={handleDeleteOrder} className="profile-section-button">
                            Delete Order
                        </button>
                    </div>
                </div>

                {/* ТАБЛИЦЯ ЗАМОВЛЕНЬ */}
                {successMessage && <p className="success-message">{successMessage}</p>}
                <div className="transaction-scroll">
                    <h2>All Orders</h2>
                    <table className="transaction-table">
                        <thead>
                        <tr>
                            <th>Select</th>
                            <th>ID</th>
                            <th>Address</th>
                            <th>Email</th>
                            <th>Weight</th>
                            <th>Distance</th>
                            <th>Price</th>
                            <th>Status</th>
                            <th>Type</th>
                            <th>Created</th>
                            <th>Delivered</th>
                        </tr>
                        </thead>
                        <tbody>
                        {orders.map(order => (
                            <tr key={order.orderId}>
                                <td>
                                    <input
                                        type="radio"
                                        name="selectedOrder"
                                        onChange={() => setSelectedOrderId(order.orderId)}
                                        checked={selectedOrderId === order.orderId}
                                    />
                                </td>
                                <td>{order.orderId}</td>
                                <td>{order.address}</td>
                                <td>{order.userEmail}</td>
                                <td>{order.weight} kg</td>
                                <td>{order.distance} km</td>
                                <td>${parseFloat(order.price).toFixed(2)}</td>
                                <td>{order.status}</td>
                                <td>{order.type}</td>
                                <td>{new Date(order.createTime).toLocaleString()}</td>
                                <td>{order.deliveryTime ? new Date(order.deliveryTime).toLocaleString() : "-"}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                {/* ТАБЛИЦЯ СКЛАДІВ */}
                <div className="transaction-scroll" style={{ marginTop: "40px" }}>
                    <h2>Warehouses</h2>
                    <table className="transaction-table">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Address</th>
                            <th>Lat</th>
                            <th>Lon</th>
                            <th>Max Cap.</th>
                            <th>Load</th>
                            <th>Contact</th>
                            <th>Type</th>
                        </tr>
                        </thead>
                        <tbody>
                        {warehouses.map(w => (
                            <tr key={w.warehouseId}>
                                <td>{w.warehouseId}</td>
                                <td>{w.address}</td>
                                <td>{w.latitude}</td>
                                <td>{w.longitude}</td>
                                <td>{w.maxCapacity}</td>
                                <td>{w.currentLoad}</td>
                                <td>{w.responsibleContact}</td>
                                <td>{w.type}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    );

}

export default ManagerPanel;
