# 📦 DeliverIQ — Microservice Logistics System

## Overview

DeliverIQ is a backend-focused microservice logistics system developed as part of a course project. The system is built using **Java**, **Spring Boot**, **Kafka**, and follows the **Clean Architecture** and **Domain-Driven Design (DDD)** principles.

The application provides services for:
- Managing delivery routes
- Handling user orders and payments
- Integrating with external APIs like OSRM for routing and Stripe/CryptoCloud for payments

---

## 🔧 Tech Stack

- **Java 21**
- **Spring Boot 3**
- **Kafka** — asynchronous communication between services
- **PostgreSQL** — persistent storage
- **Docker & Docker Compose** — containerization and local orchestration
- **OpenStreetMap / OSRM** — route calculation API
- **Stripe & CryptoCloud** — fiat and crypto payments
- **React (optional)** — frontend for user interaction
- **Leaflet.js** — map rendering (if frontend is used)

---

## 🧩 Microservices

- **API Gateway** — entry point to the system (Spring Cloud Gateway)
- **Routing Service** — calculates optimal delivery paths
- **Order Service** — handles customer orders and capacity checks
- **Payment Service** — processes fiat and crypto payments
- **Notification Service** — sends alerts and confirmations
- **User Service** — manages user registration and authentication

Each service communicates via Kafka events and exposes RESTful APIs.

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Docker & Docker Compose
- Git

