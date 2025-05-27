import React, {useEffect, useState} from 'react';
import { MapContainer, TileLayer, Polyline, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';
import MapClickHandler from '../../components/MapClickHandler';
import { GeoJSON } from 'react-leaflet';
import * as turf from '@turf/turf';
//import kyivOblastGeo from '../../../public/data/UA_32_Kyivska_fixed.geojson';

function LogisticsPage() {
    const boxIcon = L.divIcon({
        className: 'custom-icon',
        html: '<div style="font-size: 26px;">📦</div>',
        iconSize: [500, 500],
        iconAnchor: [15, 15],
    });

    const clickIcon = L.divIcon({
        html: '<div style="font-size: 26px;">📌</div>', // або '🧭', '📌' — що хочеш
        iconSize: [30, 30],
        className: 'custom-marker-icon',
    });

    const warehouseIcon = L.divIcon({
        className: 'custom-icon',
        html: '<div style="font-size: 26px;">🏭</div>',
        iconSize: [30, 30],
        iconAnchor: [15, 15],
    });
    const API_URL = process.env.REACT_APP_API_URL;
    const userId = localStorage.getItem("userId");
    const email = localStorage.getItem("email");
    const token = localStorage.getItem("token");

    const [fromLat, setFromLat] = useState("");
    const [fromLng, setFromLng] = useState("");
    const [type, setType] = useState("FOOD");
    const [weight, setWeight] = useState("");

    const [toLat, setToLat] = useState("");
    const [toLng, setToLng] = useState("");
    const [orderId, setOrderId] = useState("");

    const [route, setRoute] = useState(null);
    const [price, setPrice] = useState(null);
    const [successMessage, setSuccessMessage] = useState("");
    const [balance, setBalance] = useState(null);
    const [message, setMessage] = useState("");
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [paymentType, setPaymentType] = useState("crypto");
    const [amount, setAmount] = useState("");
    const [error, setError] = useState("");
    const [selectedMarker, setSelectedMarker] = useState(null); // { lat, lng }
    const [mode, setMode] = useState("from"); // або "to"
    const [kyivOblast, setKyivOblast] = useState(null);


    const handleRouteToWarehouse = async () => {
        if (!fromLat || !fromLng || !weight) {
            alert("⚠️ Будь ласка, заповніть усі поля: координати та вагу.");
            return;
        }

        if (!isPointInKyivOblast(parseFloat(fromLat), parseFloat(fromLng))) {
            alert("⚠️ Координати вихідної точки поза межами Київської області.");
            return;
        }
        setRoute(null);
        const res = await fetch(`${API_URL}/api/route/to-warehouse`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({
                email,
                lat: parseFloat(fromLat),
                lng: parseFloat(fromLng),
                type,
                capacity: parseFloat(weight),
            }),
        });

        const data = await res.json();
        console.log("Route to warehouse:", data);
        setRoute(data);
        setPrice(data.priceUah);

        // Очистити інпути
        setFromLat("");
        setFromLng("");
        setWeight("");
    };


    const handleRouteFromWarehouse = async () => {
        const lat = parseFloat(toLat);
        const lng = parseFloat(toLng);

        if (isNaN(lat) || isNaN(lng) || !orderId) {
            alert("⚠️ Будь ласка, заповніть координати та ID замовлення.");
            return;
        }

        if (!isPointInKyivOblast(lat, lng)) {
            alert("⚠️ Координати точки доставки поза межами Київської області.");
            return;
        }

        setRoute(null);
        const res = await fetch(`${API_URL}/api/route/from-warehouse`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({
                orderId,
                toLat: lat,
                toLng: lng,
            }),
        });

        const data = await res.json();
        console.log("Route from warehouse:", data);
        setRoute(data);
        setPrice(data.priceUah);

        setOrderId("");
        setToLat("");
        setToLng("");
    };


    const handlePaymentAndOrder = async () => {
        if (!route || !price) return;

        const paymentRes = await fetch(`${API_URL}/api/details/pay-for-delivery?amount=${price}`, {
            method: 'POST',
            headers: {
                'X-User-Id': userId,
                Authorization: `Bearer ${token}`,
            },
        });

        if (!paymentRes.ok) {
            const errorText = await paymentRes.text();
            let errorMessage;

            try {
                const errorJson = JSON.parse(errorText);
                errorMessage = errorJson.message || "Помилка під час оплати.";
            } catch (e) {
                errorMessage = "Помилка під час оплати.";
            }

            setSuccessMessage(<span className="error-message">❌ {errorMessage}</span>);
            setTimeout(() => setSuccessMessage(""), 4000);
            return;
        }

        let capacityValue = weight;
        if (!capacityValue && orderId) {
            const capRes = await fetch(`${API_URL}/api/order/get-capacity?orderId=${orderId}`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });

            capacityValue = await capRes.json();

        }

        const newOrderPayload = {
            capacity: capacityValue,
            userId,
            email,
            routeResponseDTO: route
        };

        console.log(newOrderPayload)

        await fetch(`${API_URL}/api/order/new-order`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(newOrderPayload),
        });

        // Повідомлення + Очистити маршрут і ціну
        setSuccessMessage("Замовлення створено і оплачено успішно!");
        setRoute(null);
        setPrice(null);
        getBalance(); // 🔄 оновлюємо баланс
        setTimeout(() => setSuccessMessage(""), 3000);
    };

    function getBalance() {
        fetch(`${API_URL}/api/details/balance?userId=${encodeURIComponent(userId)}`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
                if (!response.ok) throw new Error("Failed to fetch balance");
                return response.json();
            })
            .then((data) => setBalance(data))
            .catch((err) => console.error("Error fetching balance:", err));
    }

    function handleCreatePayment() {
        const numericAmount = parseFloat(amount);
        if (!numericAmount || numericAmount <= 0) {
            setError("Enter valid amount.");
            return;
        }

        const endpoint = paymentType === "crypto"
            ? `${API_URL}/api/crypto/create-payment`
            : `${API_URL}/api/fiat/create-payment`;

        fetch(endpoint, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
                "X-User-Id": userId,
            },
            body: JSON.stringify({ amount: numericAmount.toString(), currency: "USD" }),
        })
            .then((response) => {
                const contentType = response.headers.get("Content-Type");
                if (contentType && contentType.includes("application/json")) {
                    return response.json();
                } else {
                    return response.text();
                }
            })
            .then((data) => {
                const link = typeof data === "string" ? data : data.paymentUrl;
                if (link && link.startsWith("http")) {
                    window.location.href = link;
                } else {
                    throw new Error("No valid payment URL returned.");
                }
            })
            .catch((err) => setError(err.message));
    }

    function isPointInKyivOblast(lat, lng) {
        if (!kyivOblast) return false;
        const point = turf.point([lng, lat]);
        return turf.booleanPointInPolygon(point, kyivOblast.features[0]);
    }

    useEffect(() => {
        getBalance();
        fetch("/data/UA_32_Kyivska_fixed.geojson")
            .then((res) => res.json())
            .then((geo) => {
                setKyivOblast(geo);
            })
            .catch((err) => console.error("❌ GeoJSON load error:", err));
    }, []);


    return (
        <div className="Orders-container">
            <div className="OrdersPage">
                <h1 className="orders-title">Logistics Panel</h1>
                <div style={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    gap: "20px",
                    marginBottom: "20px"
                }}>
                    <button className="addBalance" onClick={() => {
                        setPaymentType("crypto");
                        setIsModalOpen(true);
                    }}>Add Crypto
                    </button>
                    <p style={{
                        fontWeight: "bold",
                        fontSize: "22px",       // 🔼 Збільшений розмір тексту
                        marginLeft: "10px"      // 🔼 Зміщення трохи вліво
                    }}>
                        Balance: {balance !== null ? `${balance.toFixed(2)} $` : "..."}
                    </p>

                    <button className="addBalance" onClick={() => {
                        setPaymentType("fiat");
                        setIsModalOpen(true);
                    }}>Add Fiat
                    </button>
                </div>
                {isModalOpen && (
                    <div className="modal-overlay">
                        <div className="modal-content">
                            <button className="close-button" onClick={() => setIsModalOpen(false)}>✖</button>
                            <h3>Поповнення балансу ({paymentType.toUpperCase()})</h3>
                            <input
                                type="number"
                                placeholder="Сума"
                                className="modal-input"
                                value={amount}
                                onChange={(e) => setAmount(e.target.value)}
                            />
                            {error && <p style={{color: "red"}}>{error}</p>}
                            <button className="modal-button" onClick={handleCreatePayment}>Створити оплату</button>
                        </div>
                    </div>
                )}


                {successMessage && <p className="success-message">{successMessage}</p>}

                <div className="manager-controls">
                    {/* Left Block */}
                    <div className="manager-controls-block right">
                        <h3 style={{marginBottom: "5px"}}>To Warehouse</h3>
                        <input
                            type="number"
                            placeholder="Lat"
                            className="logistics-input"
                            value={fromLat}
                            onChange={(e) => setFromLat(e.target.value)}
                        />
                        <input
                            type="number"
                            placeholder="Lng"
                            className="logistics-input"
                            value={fromLng}
                            onChange={(e) => setFromLng(e.target.value)}
                        />
                        <input
                            type="number"
                            placeholder="Weight (t)"
                            className="logistics-input"
                            value={weight}
                            onChange={(e) => setWeight(e.target.value)}
                        />
                        <select
                            className="logistics-select"
                            value={type}
                            onChange={(e) => setType(e.target.value)}
                        >
                            <option value="FOOD">FOOD</option>
                            <option value="MATERIALS">MATERIALS</option>
                            <option value="ELECTRONICS">ELECTRONICS</option>
                        </select>
                        <button className="logout-button" onClick={handleRouteToWarehouse}>
                            To Warehouse
                        </button>
                    </div>

                    {/* Center Block for Price and Button */}
                    <div className="logistics-price-payment">
                        {route && price && (
                            <>
                                <p style={{fontSize: "18px", textAlign: "center"}}>
                                    Distance: {(route.distanceMeters / 1000).toFixed(2)} km
                                </p>
                                <p style={{fontSize: "20px", fontWeight: "500", textAlign: "center"}}>
                                    Price: {price} $
                                </p>
                                <button className="pay-button" onClick={handlePaymentAndOrder}>
                                    💸 Pay Now
                                </button>
                            </>
                        )}
                    </div>

                    {/* Right Block */}
                    <div className="manager-controls-block left">
                        <h3 style={{marginBottom: "5px"}}>From Warehouse</h3>
                        <input
                            type="text"
                            placeholder="Order ID"
                            className="logistics-input"
                            style={{height: "50px"}}
                            value={orderId}
                            onChange={(e) => setOrderId(e.target.value)}
                        />
                        <input
                            type="number"
                            placeholder="Lat"
                            className="logistics-input"
                            style={{height: "50px"}}
                            value={toLat}
                            onChange={(e) => setToLat(e.target.value)}
                        />
                        <input
                            type="number"
                            placeholder="Lng"
                            className="logistics-input"
                            style={{height: "50px"}}
                            value={toLng}
                            onChange={(e) => setToLng(e.target.value)}
                        />
                        <button className="logout-button" onClick={handleRouteFromWarehouse}>
                            From Warehouse
                        </button>
                    </div>
                </div>
                <div style={{textAlign: "center", marginBottom: "10px"}}>
                    <button
                        onClick={() => setMode("from")}
                        className={`select-mode-button ${mode === "from" ? "active" : ""}`}
                        style={{marginRight: "10px"}}
                    >
                        Відправник
                    </button>
                    <button
                        onClick={() => setMode("to")}
                        className={`select-mode-button ${mode === "to" ? "active" : ""}`}
                    >
                        Отримувач
                    </button>
                </div>



                {/* Map */}
                <MapContainer
                    center={[50.45, 30.52]}
                    zoom={10}
                    style={{ height: "500px", width: "100%", marginTop: "30px" }}
                >
                    <TileLayer
                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        attribution="&copy; OpenStreetMap contributors"
                    />

                    <GeoJSON
                        data={kyivOblast}
                        style={{
                            color: "black",
                            weight: 2,
                            fillOpacity: 0.05
                        }}
                        onEachFeature={(feature, layer) => {
                            layer.bindPopup(feature.properties.name || "No name");
                        }}
                    />




                    {/* Клік на мапі → запис координат */}
                    <MapClickHandler
                        onClick={({ lat, lng }) => {
                            if (!isPointInKyivOblast(lat, lng)) {
                                alert("⚠️ Ви вийшли за межі Київської області.");
                                return;
                            }
                            if (mode === "from") {
                                setFromLat(lat);
                                setFromLng(lng);
                            } else {
                                setToLat(lat);
                                setToLng(lng);
                            }
                            setSelectedMarker({ lat, lng });
                        }}
                    />

                    {/* Обрана точка користувачем */}
                    {selectedMarker && (
                        <Marker
                            position={[selectedMarker.lat, selectedMarker.lng]}
                            icon={clickIcon}
                        >
                            <Popup>Обрана точка</Popup>
                        </Marker>
                    )}

                    {/* Маршрут, якщо є */}
                    {route?.geoJsonRoute?.coordinates && (
                        <>
                            <Polyline
                                positions={route.geoJsonRoute.coordinates.map(([lng, lat]) => [lat, lng])}
                                color="blue"
                            />
                            <Marker
                                position={[
                                    route.geoJsonRoute.coordinates[0][1],
                                    route.geoJsonRoute.coordinates[0][0],
                                ]}
                                icon={boxIcon}
                            />
                            <Marker
                                position={[
                                    route.geoJsonRoute.coordinates.at(-1)[1],
                                    route.geoJsonRoute.coordinates.at(-1)[0],
                                ]}
                                icon={warehouseIcon}
                            />
                        </>
                    )}
                </MapContainer>



            </div>
        </div>
    );

}

export default LogisticsPage;
