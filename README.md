# Loan Moratorium System

A full-stack Loan Moratorium Management System for banks and financial institutions, enabling users to register, apply for loans, request moratoriums, and manage repayments. Built with Spring Boot (Java), React, PostgreSQL, and Docker Compose.

---

## Table of Contents
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Clone the Repository](#clone-the-repository)
  - [Database Setup](#database-setup)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
  - [Docker Compose Setup](#docker-compose-setup)
  - [Environment Variables](#environment-variables)
- [API Endpoints](#api-endpoints)
- [Usage Examples](#usage-examples)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)
- [Author](#author)

---

## Features
- User registration and login with secure password hashing
- JWT-ready authentication endpoints
- Apply for loans, approve/reject loans, and request moratoriums
- Role-based access control (user/admin)
- RESTful API with CORS support for frontend integration
- Docker Compose for easy local development

---

## Architecture
```
[ React Frontend ] <----HTTP----> [ Spring Boot Backend ] <----JDBC----> [ PostgreSQL DB ]
```
- **Frontend:** React (port 3000)
- **Backend:** Spring Boot (port 8081, context path `/api`)
- **Database:** PostgreSQL (port 5432 or 5433)

---

## Tech Stack
- **Backend:** Java 17, Spring Boot, Spring Security, Spring Data JPA
- **Frontend:** React, JavaScript/TypeScript, Axios/Fetch
- **Database:** PostgreSQL
- **Containerization:** Docker, Docker Compose
- **Build Tools:** Maven, npm

---

## Project Structure
```
loan-moratorium-app/
├── backend/                # Spring Boot backend
├── frontend/               # React frontend
├── docker-compose.yml      # Docker Compose setup
├── README.md               # Project documentation
└── ...
```

---

## Getting Started

### Prerequisites
- Java 17+
- Node.js (v16+ recommended)
- PostgreSQL (local or via Docker)
- Maven
- Git
- Docker & Docker Compose (optional, for containerized setup)

### Clone the Repository
```bash
git clone https://github.com/Srikar-Kondepudi/loan-moratorium-app.git
cd loan-moratorium-app
```

### Database Setup
**Option 1: Local PostgreSQL**
```bash
psql -U postgres
CREATE DATABASE loan_moratorium;
\q
```
- Update `backend/src/main/resources/application.properties` if needed.

**Option 2: Docker Compose**
```bash
docker-compose up --build
```

### Backend Setup
```bash
cd backend
./mvnw spring-boot:run
```
- The backend runs at [http://localhost:8081/api](http://localhost:8081/api)

### Frontend Setup
```bash
cd frontend
npm install
npm start
```
- The frontend runs at [http://localhost:3000](http://localhost:3000)

### Environment Variables
Create a `.env` file in the `frontend` directory:
```
REACT_APP_API_URL=http://localhost:8081/api
```

---

## API Endpoints

### Auth
- `POST   /api/auth/register` — Register a new user
- `POST   /api/auth/login` — Login and receive user info
- `GET    /api/auth/me` — Get current user info (token required)

### Loans
- `POST   /api/loans` — Apply for a loan
- `GET    /api/loans` — List user loans
- `POST   /api/loans/{id}/moratorium` — Request moratorium
- `POST   /api/loans/{id}/approve` — Approve loan (admin)

---

## Usage Examples

### Register a User
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{
  "username": "testuser",
  "password": "testpass123",
  "email": "testuser@example.com",
  "fullName": "Test User"
}'
```

### Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
-H "Content-Type: application/json" \
-d '{
  "username": "testuser",
  "password": "testpass123"
}'
```

---

## Troubleshooting
- **403 Forbidden on /auth/login:** Ensure `SecurityConfig` uses `.requestMatchers("/auth/**").permitAll()` and backend is restarted.
- **CORS errors:** Check CORS settings in backend and frontend API URL.
- **Database connection errors:** Verify PostgreSQL is running and credentials in `application.properties` are correct.
- **Port conflicts:** Make sure no other services are using ports 8081, 3000, or 5432/5433.
- **Frontend not connecting:** Check `.env` in frontend and restart `npm start` after changes.

---

## Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Create a new Pull Request

---

## License
MIT

---

## Author
- [Srikar Kondepudi](https://github.com/Srikar-Kondepudi)
