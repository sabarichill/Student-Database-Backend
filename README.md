# 🎓 Student Database Management System — Backend

A Spring Boot REST API that powers the Student Database Management System. It exposes CRUD, search, sort, filter, and stats endpoints backed by a MySQL database via Spring Data JPA/Hibernate. The [Student-Database-Frontend](../Student-Database-Frontend) React app consumes this API.

## How the Project Works

The backend follows a standard layered Spring Boot architecture:

```
Controller  →  Service  →  Repository  →  MySQL (via Hibernate/JPA)
```

- **`StudentController`** — exposes REST endpoints under `/api/students`, delegates all logic to the service layer, and returns proper HTTP status codes (`201 Created`, `200 OK`, etc.).
- **`StudentService`** — contains the business logic (searching, sorting, filtering, counting) and calls the repository.
- **`StudentRepository`** — a Spring Data JPA interface that talks to the `students` table (no manual SQL needed for basic operations).
- **`Student`** (model/entity) — maps to the `students` table; fields include name, email, age, department, course, phone, address, CGPA, year of passing (`yop`), and an auto-set `created_at` timestamp.
- **`GlobalExceptionHandler`** / **`StudentNotFoundException`** — centralized exception handling so a missing student ID returns a clean `404` instead of a stack trace.
- **`CorsConfig`** — allows the React frontend (running on a different origin/port) to call this API.

Hibernate auto-creates/updates the `students` table on startup (`spring.jpa.hibernate.ddl-auto=update`), so you don't need to manually write schema SQL for the base setup.

## Tech Stack

|       Layer      |                  Technology                   |
|------------------|-----------------------------------------------|
| Language         | Java 17                                       |
| Framework        | Spring Boot 3.2 (Spring Web, Spring Data JPA) |
| ORM              | Hibernate                                     |
| Database         | MySQL                                         |
| Build Tool       | Maven                                         |
| Containerization | Docker (multi-stage build)                    |
| Testing          | JUnit 5 (`spring-boot-starter-test`)          |

## Project Structure

```
src/main/java/com/studentdbms/
├── controller/
│   └── StudentController.java     # REST endpoints
├── service/
│   └── StudentService.java        # Business logic
├── repository/
│   └── StudentRepository.java     # Spring Data JPA repository
├── model/
│   └── Student.java               # JPA entity
├── exception/
│   ├── StudentNotFoundException.java
│   └── GlobalExceptionHandler.java
├── CorsConfig.java                # CORS setup for frontend access
└── StudentDbmsApplication.java    # Main Spring Boot entry point
```

## API Endpoints

Base path: `/api/students`

|          Action         | Method |                 Endpoint                        |
|-------------------------|--------|-------------------------------------------------|
| Create student          | POST   | `/api/students`                                 |
| Get all students        | GET    | `/api/students`                                 |
| Get student by ID       | GET    | `/api/students/{id}`                            |
| Update student          | PUT    | `/api/students/{id}`                            |
| Delete student          | DELETE | `/api/students/{id}`                            |
| Search by keyword       | GET    | `/api/students/search?keyword=...`              |
| Sort students           | GET    | `/api/students/sort?sortBy=...&order=asc\|desc` |
| Filter by department    | GET    | `/api/students/filter?department=...`           |
| Get total count         | GET    | `/api/students/count`                           |
| Get list of departments | GET    | `/api/students/departments`                     |

**Example — create a student:**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Arjun Kumar",
    "email": "arjun@example.com",
    "age": 21,
    "department": "CSE",
    "course": "B.Tech",
    "phone": "9876543210",
    "cgpa": 8.7,
    "yop": 2026
  }'
```

## How to Build & Run

### Prerequisites
- Java 17 (JDK)
- Maven (or use the included wrapper `./mvnw`)
- A running MySQL instance and a database created for this project

### 1. Configure the Database

The app reads its DB connection from **environment variables** (see `application.properties`):

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/student_dbms
export SPRING_DATASOURCE_USERNAME=your_mysql_user
export SPRING_DATASOURCE_PASSWORD=your_mysql_password
```

On Windows (PowerShell):
```powershell
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/student_dbms"
$env:SPRING_DATASOURCE_USERNAME="your_mysql_user"
$env:SPRING_DATASOURCE_PASSWORD="your_mysql_password"
```

### 2. Run Locally

```bash
git clone https://github.com/sabarichill/Student-Database-Backend.git
cd Student-Database-Backend

./mvnw clean install
./mvnw spring-boot:run
```

The API starts on **`http://localhost:8080`** by default (configurable via the `PORT` env variable).

### 3. Run with Docker

```bash
docker build -t student-dbms-backend .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://<host>:3306/student_dbms \
  -e SPRING_DATASOURCE_USERNAME=your_mysql_user \
  -e SPRING_DATASOURCE_PASSWORD=your_mysql_password \
  student-dbms-backend
```

The Dockerfile uses a multi-stage build: Stage 1 compiles the JAR with Maven, Stage 2 runs it on a lightweight JDK image.

## Running Tests

```bash
./mvnw test
```

This runs the JUnit 5 test suite, including `BackendApplicationTests`, which currently verifies the Spring application context loads correctly (a basic smoke test). To extend test coverage, add:
- **Unit tests** for `StudentService` (mock the repository with Mockito).
- **Integration tests** for `StudentController` using `@SpringBootTest` + `MockMvc` or `@WebMvcTest`, covering create/read/update/delete/search/sort/filter flows and edge cases (e.g. student not found → 404).

## Connecting the Frontend

Make sure `@CrossOrigin(origins = "*")` (already set in `StudentController`) stays enabled — or restrict it to your frontend's actual origin in production — and update the frontend's `BASE_URL` (in `src/services/api.js`) to point at wherever this backend is deployed.

## Possible Improvements

- Add request validation (`@Valid` + Bean Validation annotations) on the `Student` entity for fields like email format and required fields.
- Add pagination (`Pageable`) to `GET /api/students` for large datasets.
- Restrict CORS to specific origins instead of `*` in production.
- Add Swagger/OpenAPI documentation (`springdoc-openapi`) for interactive API docs.
- Externalize DB config into `application.properties` profiles (`application-dev.properties`, `application-prod.properties`).
