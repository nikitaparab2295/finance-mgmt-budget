# Budget

The **Budget Service** manages user budgets for different categories (Groceries, Travel, Utilities, etc).  
It supports full CRUD operations and is used by the **Expense Service** to verify budget utilization.

---

## 🚀 Tech Stack
- Spring Boot 3.5.8
- Java + Gradle (KTS)
- Spring Data JPA + H2 In-Memory Database
- Feign Client (for inter-service communication)
- Springdoc OpenAPI (Swagger)
- Docker & Docker Compose

---

## 📁 Folder Structure


budget
├── src/main/java
├── src/test/java
├── build.gradle.kts
├── application.yml
└── README.md


---

## ▶️ Run Locally (Without Docker)

```bash
cd finance-mgmt-budget
./gradlew bootRun

Service runs at:
👉 http://localhost:8081

Service will be available on port 8081.
📘 Swagger API Docs
After running:
👉 http://localhost:8081/swagger-ui.html
👉 http://localhost:8081/v3/api-docs
👉 http://localhost:8081/v3/api-docs.yaml
Or 
Import the OpenAPI spec budget-openapi.yaml into Postman or other tools.

📮 Postman Collection
Import the provided budget-postman-collection.json file into Postman.

Budget APIs included:
POST /budgets
GET /budgets/{id}
SEARCH /budgets?ownerId={ownerId}&category={category}&month={month}
PATCH/budgets/{id}
DELETE /budgets/{id}

🗃️ H2 Database Console
👉 http://localhost:8081/h2-console

JDBC URL: jdbc:h2:mem:budgetdb

✔️ Testing
Unit tests are inside:
src/test/java/com/example/budget

Run:
./gradlew test
