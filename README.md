# 📦 Warehouse Service

A microservice responsible for managing warehouse operations such as inventory, stock levels, product tracking, and warehouse locations. This service is designed to integrate with other services in a distributed system (e.g., order, product, shipping).

## 🚀 Features

- Add, update, and delete warehouse locations
- Manage stock levels per product and warehouse
- Track inventory availability
- RESTful API endpoints with secured access
- Integration-ready with messaging/event-driven architecture (e.g., Kafka, RabbitMQ)

## 🛠️ Tech Stack

- **Language**: Java 17+
- **Framework**: Spring Boot
- **Database**: PostgreSQL / MySQL
- **Persistence**: JPA / Hibernate / R2DBC (if reactive)
- **API Docs**: Swagger / SpringDoc
- **Security**: JWT + Spring Security
- **Dev Tools**: Flyway (DB migrations), Docker, Lombok

## 📂 Project Structure

warehouse-service/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── config/
└── exception/


## 🔐 API Security

- Uses JWT-based authentication
- Role-based access control (e.g., ADMIN, MANAGER)

## 📈 Sample Endpoints

| Method | Endpoint                      | Description                  |
|--------|-------------------------------|------------------------------|
| GET    | `/api/warehouses`             | Get all warehouses           |
| POST   | `/api/warehouses`             | Create new warehouse         |
| GET    | `/api/stock/{productId}`      | Get stock for a product      |
| PUT    | `/api/stock/update`           | Update stock level           |

## 🧪 Testing

- Unit & integration tests using JUnit and MockMvc
- Test coverage tools (Jacoco, etc.)

## 🐳 Running with Docker

```bash
docker build -t warehouse-service .
docker run -p 8080:8080 warehouse-service
