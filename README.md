# Genesis Resources – User Management API

Spring Boot REST API for managing users. 
Valid changes are persisted in the database.

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

## 📌 Run the application

Before running the application, make sure to set up the database and import the Postman collection:

1. **Set up the test database**
   - Import the SQL dump located in the `db/` folder into your local MariaDB instance.

2. **Load the Postman collection**
   - Open Postman and import the collection from the `postman/` folder.

3. Start the **Person ID Provider** helper service:  
   [https://github.com/Tomas-Weber1994/personid-provider](https://github.com/Tomas-Weber1994/personid-provider)

4. Start the **Genesis Resources – User Management API** application.

5. Now you can send requests using Postman.
