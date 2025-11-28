# Budget Service

The **Budget Service** manages user budgets for different categories (Groceries, Travel, Utilities, etc.).
It supports full CRUD operations and is used by the **Expense Service** to verify budget utilization.

---

## Tech Stack

* Spring Boot 3.5.8
* Java + Gradle (KTS)
* Spring Data JPA + H2 In-Memory Database
* Feign Client (for inter-service communication)
* Springdoc OpenAPI (Swagger)
* Docker & Docker Compose

---

## Folder Structure

```
budget
├── src/main/java
├── src/test/java
├── build.gradle.kts
├── application.yml
└── README.md
```

---

## Run Locally (Without Docker)

```bash
cd finance-mgmt-budget
./gradlew bootRun
```

**Service runs at:**
[http://localhost:8081](http://localhost:8081)

---

## API Documentation (Swagger)

After starting the service:

* [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
* [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)
* [http://localhost:8081/v3/api-docs.yaml](http://localhost:8081/v3/api-docs.yaml)

Swagger specifications: budget-openapi.yaml
You can also import the provided `budget-postman-collection.json` into Postman or any API tool.

---

## APIs

* **POST** `/budgets`
* **GET** `/budgets/{id}`
* **GET** `/budgets?ownerId={ownerId}&category={category}&month={month}`
* **PATCH** `/budgets/{id}`
* **DELETE** `/budgets/{id}`

---

## H2 Database Console

URL: [http://localhost:8081/h2-console](http://localhost:8081/h2-console)
**JDBC URL:** `jdbc:h2:mem:budgetdb`

---

## Tests

Unit tests are located in:

```
src/test/java/com/example/budget
```

Run tests using:

```bash
./gradlew test
```
---
## Docker Instructions

docker build -t finance-mgmt-budget .
docker run -p 8081:8081 finance-mgmt-budget
