import React, { useEffect, useState } from "react";

function Orders() {
    const [orders, setOrders] = useState([]);
    const [error, setError] = useState("");
    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("token");
    const API_URL = process.env.REACT_APP_API_URL;

    useEffect(() => {
        fetch(`${API_URL}/api/order/get`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
                "X-User-Id": userId,
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
                if (!response.ok) {
                    return response.text().then((text) => {
                        throw new Error(text || "Failed to fetch orders");
                    });
                }
                return response.json();
            })
            .then((data) => {
                setOrders(data);
            })
            .catch((err) => {
                console.error(err);
                setError("Failed to load orders");
            });
    }, [API_URL, userId, token]);

    const renderOrdersTable = (title, list) => (
        <div className="transaction-scroll" style={{ marginTop: "30px" }}>
            <h2>{title}</h2>
            {list.length > 0 ? (
                <table className="transaction-table">
                    <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Warehouse ID</th>
                        <th>Address</th>
                        <th>Weight</th>
                        <th>Distance</th>
                        <th>Type</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th>Created</th>
                        <th>Delivery</th>
                    </tr>
                    </thead>
                    <tbody>
                    {list.map((order) => (
                        <tr key={order.orderId}>
                            <td>{order.orderId}</td>
                            <td>{order.warehouseId}</td>
                            <td>{order.address}</td>
                            <td>{order.weight} t</td>
                            <td>{order.distance} km</td>
                            <td>{order.type}</td>
                            <td>${parseFloat(order.price).toFixed(2)}</td>
                            <td>{order.status}</td>
                            <td>{new Date(order.createTime).toLocaleString()}</td>
                            <td>{order.deliveryTime ? new Date(order.deliveryTime).toLocaleString() : "-"}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <p>No orders in this category.</p>
            )}
        </div>
    );

    const createdOrders = orders.filter((o) => o.status === "CREATED");
    const processingOrders = orders.filter((o) => o.status === "PROCESSING");
    const deliveredOrders = orders.filter((o) =>
        ["DELIVERED_TO_BASE", "DELIVERED_TO_CLIENT"].includes(o.status)
    );

    return (
        <div className="Orders-container">
            <div className="OrdersPage">
                <h1 className="orders-title">My Orders</h1>
                {error && <p className="error-message">{error}</p>}

                {orders.length > 0 ? (
                    <>
                        {renderOrdersTable("Created Orders", createdOrders)}
                        {renderOrdersTable("Processing Orders", processingOrders)}
                        {renderOrdersTable("Delivered Orders", deliveredOrders)}
                    </>
                ) : (
                    <p>No orders found.</p>
                )}
            </div>
        </div>
    );
}

export default Orders;
