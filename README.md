# Genesis Resources – User Management API

Spring Boot REST API for managing users, structured as **three microservices**:

1. **Database** – MariaDB for persisting user data.
2. **Person ID Provider** – External helper service simulation for validating `personId`.
3. **Genesis Resources** – Main application providing user management endpoints.

---

## 🚀 Tech stack
- **Java 21+**
- **Spring Boot**
- **Spring Data JPA (Hibernate)** – persistence
- **MariaDB (default database)**
- **Lombok** – logging and boilerplate reduction
- **Jakarta Validation** – input validation

---

## 📦 User Entity

| Column    | Type     | Constraints                          |
|-----------|--------- |--------------------------------------|
| `id`      | Long     | PK, Unique, NotNull, AutoIncrement  |
| `name`    | Varchar  | NotNull                             |
| `surname` | Varchar  | NotNull                             |
| `personId`| Varchar  | Unique, NotNull, 12 alphanumeric characters |
| `uuid`    | Varchar  | Unique, NotNull (generated automatically) |

> **Note:** The `personId` is validated against an external helper service (Person ID Provider). See [personid-provider](https://github.com/Tomas-Weber1994/personid-provider) for details.

---

## 📑 API Endpoints

| Method   | Endpoint              | Description                     |
|----------|-----------------------|---------------------------------|
| `POST`   | `/api/v1/users`       | Create a new user               |
| `GET`    | `/api/v1/users`       | Retrieve list of users          |
| `GET`    | `/api/v1/users/{id}`  | Retrieve one user (lite or detailed) |
| `PUT`    | `/api/v1/users`       | Update an existing user         |
| `DELETE` | `/api/v1/users/{id}`  | Delete a user                   |

---

## 🛡️ Validation & Exceptions

- `@NotBlank` → `name`, `surname`
- `@Pattern` → `personId` must be **12 alphanumeric characters**
- `uuid` → generated automatically, cannot be set by client
- Errors are handled globally and returned in JSON

---

## 📝 Logging

- **Lombok `@Slf4j`** is used
- Service layer logs major operations (`info`)
- GlobalExceptionHandler logs handled exceptions (`warn`/`error`)

---
## ⚙️ Base Settings

#### Clone the repository
```bash
git clone https://github.com/Tomas-Weber1994/genesis-resources.git
cd genesis-resources
```

#### Create your `.env` file
```bash
cp .env.example .env
```
- Edit the `.env` file with your own database credentials and Person ID service URL.

#### Load the Postman collection
- Open Postman and import the collection from the `postman/` folder.
---

## 📌 Option 1 – Quick Start via Docker Compose (preferred)

#### Start all services:
```bash
docker compose up -d
```

#### Access APIs:

- Genesis Resources: http://localhost:8080
- Person ID Provider: http://localhost:8081

#### Stop services when done:
```bash
docker compose down
```
---

## 📌 Option 2 – Run via IDE / Local Run
- Start the Person ID Provider helper service: (`https://github.com/Tomas-Weber1994/personid-provider`)

- Start the Genesis Resources – User Management API application in your IDE (e.g., IntelliJ).

---
## ✅ Finally

- You can now send requests using Postman to interact with the API.
