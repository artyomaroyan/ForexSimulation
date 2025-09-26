# ForexSimulation Microservices

## Project Overview
This project is a microservices-based Forex Simulation platform, consisting of:
- **customer-service**: Manages customer data
- **order-service**: Handles forex orders
- **currency-service**: Manages currency information

Each service is a Spring Boot application with a PostgreSQL database, orchestrated via Docker Compose.

---

## Prerequisites
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/)

---

## Building & Running with Docker

1. **Clone the repository**
2. **Build and start all services:**
   ```sh
   docker-compose up --build
   ```
   This will build all services and start them along with their respective PostgreSQL databases.

3. **Access the services:**
   - Customer Service: [http://localhost:8081](http://localhost:8081)
   - Order Service: [http://localhost:8082](http://localhost:8082)
   - Currency Service: [http://localhost:8083](http://localhost:8083)

---

## API Documentation

### Customer Service
- **Base URL:** `http://localhost:8081`
- **Endpoints:**
  - `GET /customers/{id}` — Get customer by ID
  - `POST /customers/create` — Create a new customer
    - Body: `{ "name": "John Doe", "email": "john@example.com"}`

### Order Service
- **Base URL:** `http://localhost:8082`
- **Endpoints:**
  - `GET /orders/{id}` — Get order by ID
  - `POST /orders` — Create a new order
    - Body: `{ "currencyFrom": "USD", "currencyTo": "AMD", "amount": 1000 }`

### Currency Service
- **Base URL:** `http://localhost:8083`
- **Endpoints:**
  - `GET /rates` — Get current rate
  - `POST /simulate` — Simulate rate change

---

## Sample Requests & Expected Responses

### Customer Service
- **Create Customer**
  ```sh
  curl -X POST http://localhost:8081/api/v1/customers/create -H "Content-Type: application/json" -d '{"name":"John Doe","email":"john@example.com"}'
  ```
  **Response:**
  ```json
  { "id": 1, "name": "John Doe", "email": "john@example.com" }
  ```

### Order Service
- **Create Order**
  ```sh
  curl -X POST http://localhost:8082/api/v1/orders/create -H "Content-Type: application/json" -d '{"currencyFrom": "USD", "currencyTo": "AMD", "amount": 1000}'
  ```
  **Response:**
  ```json
  { "id": 1, "currencyFrom": "USD", "currencyTo": "AMD", "amount": 1000 }
  ```
---

## Swagger/OpenAPI
If enabled, Swagger UI is typically available at `/swagger-ui.html` for each service (e.g., `http://localhost:8081/swagger-ui.html`).

---

## Notes
- Update API endpoints and sample payloads as needed to match your actual implementation.
- Ensure Docker is running before executing `docker-compose up --build`.

