# Room Booking Application

This is a Spring Boot RESTful service for managing hotel room bookings. It provides endpoints to create and list rooms, make and cancel bookings, and view booking details. The application uses an in-memory H2 database and includes Swagger/OpenAPI documentation.

---
## Project Overview

A simple service to manage hotel rooms and bookings. Users can:

- **Rooms**: list all rooms or only those available, create new rooms
- **Bookings**: make a new booking, cancel an existing booking, retrieve booking details

The in-memory H2 database is reset each time the application restarts.

---

## Design & Documentation

This section provides a high-level overview of the project's structure and key components:

- **Controller Layer** (`RoomBooking.demo.Controller`):
  - `RoomController` — handles `/rooms` endpoints (list, filter by availability, create rooms).
  - `BookingController` — handles `/bookings` endpoints (create, cancel, retrieve bookings).

- **Service Layer** (`RoomBooking.demo.Service` & `ServiceImpl`):
  - Interfaces: `RoomService`, `BookingService` define business operations.
  - Implementations (`RoomServiceImpl`, `BookingServiceImpl`) contain business logic and transaction management.

- **Repository Layer** (`RoomBooking.demo.Repository`):
  - `RoomRepository`, `BookingRepository` extend Spring Data JPA for CRUD operations.

- **Entity Layer** (`RoomBooking.demo.Entity`):
  - `Room`, `Booking` represent database tables, extend `BaseEntity` (auto-generated id, timestamps).

- **DTO & Mapping** (`RoomBooking.demo.Extra.DTO`, `RoomBooking.demo.Mapper`):
  - Data Transfer Objects (`RoomDTO`, `BookingDTO`) for requests/responses.
  - MapStruct mappers (`RoomMapper`, `BookingMapper`) to convert between Entities and DTOs.

- **Validation & Exception Handling** (`RoomBooking.demo.Extra`, `ExceptionHandling`):
  - `BookingRequest` enforces request payload constraints (e.g., future dates).
  - Custom exceptions (`NotFoundException`, `BusinessException`) with a global handler (`GlobalExceptionHandler`) returning `ApiError` responses.

- **Persistence**: In-memory H2 database configured in `application.properties`. Schema and data are reset on each restart.

- **API Documentation**: OpenAPI/Swagger UI automatically generated via SpringDoc, accessible at `/swagger-ui.html`.

## Technologies & Libraries

- **Java**: version 17
- **Spring Boot**: 3.2.5
- **Spring MVC** (`spring-boot-starter-web`)
- **Spring Data JPA** (`spring-boot-starter-data-jpa`)
- **Bean Validation** (`spring-boot-starter-validation`)
- **H2 Database** (`com.h2database:h2`)
- **Lombok** (`org.projectlombok:lombok`)
- **MapStruct** (`org.mapstruct:mapstruct`, `lombok-mapstruct-binding`)
- **OpenAPI/Swagger UI** (`org.springdoc:springdoc-openapi-starter-webmvc-ui`)
- **Test**: `spring-boot-starter-test`

---

## Requirements

- Java 17 or higher
- Maven 3.6+  
- Git (to clone repository)

---

## Installation

1. **Clone the repository**  
   ```bash
   git clone https://your-company.git/RoomBooking.git
   cd RoomBooking
   ```
2. **Build the project**  
   ```bash
   mvn clean package
   ```

---

## Configuration

All configuration is in `src/main/resources/application.properties`:

```properties
spring.application.name=RoomBooking

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# DataSource (in-memory)
spring.datasource.url=jdbc:h2:mem:hoteldb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=

# JPA / Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Logging
tlogging.level.RoomBooking.demo.ServiceImpl=debug
```

---

## Running the Application

- **From Maven**:
  ```bash
  mvn spring-boot:run
  ```
- **From the JAR**:
  ```bash
  java -jar target/RoomBooking-0.0.1-SNAPSHOT.jar
  ```

The service runs on **port 8080** by default.

---

## H2 Database Console

Visit `http://localhost:8080/h2-console` to explore the in-memory database.

- **JDBC URL**: `jdbc:h2:mem:hoteldb`  
- **User Name**: `sa`  
- **Password**: (leave blank)

---

## API Reference

Base URL: `http://localhost:8080`

### Rooms

#### GET `/rooms`

- **Query Parameters (params)**:
  - `available` (Boolean, optional): filter by availability (`true` or `false`). If omitted, returns all rooms.
- **Responses**:
  - `200 OK` – JSON array of `RoomDTO`

**Example Response**:
```json
[
  {
    "id": 1,
    "roomNumber": "101A",
    "capacity": 2,
    "pricePerNight": "99.99",
    "available": true
  }
]
```

#### POST `/rooms`

- **Request Body** (`RoomDTO` JSON):
  | Field          | Type      | Required | Description                       |
  | -------------- | --------- | -------- | --------------------------------- |
  | `roomNumber`   | String    | Yes      | Unique room identifier            |
  | `capacity`     | Integer   | Yes      | Number of occupants (must > 0)    |
  | `pricePerNight`| String    | Yes      | Decimal price (e.g. "120.50")   |
  | `available`    | Boolean   | No       | Defaults to `true`                |

- **Responses**:
  - `201 Created` – JSON `RoomDTO` of the newly created room

**Example Request**:
```json
{
  "roomNumber": "202B",
  "capacity": 3,
  "pricePerNight": "150.00",
  "available": true
}
```

**Example Response**:
```json
{
  "id": 2,
  "roomNumber": "202B",
  "capacity": 3,
  "pricePerNight": "150.00",
  "available": true
}
```

---

### Bookings

#### POST `/bookings`

- **Request Body** (`BookingRequest` JSON):
  | Field           | Type   | Required | Description                             |
  | --------------- | ------ | -------- | --------------------------------------- |
  | `roomId`        | Long   | Yes      | Identifier of the room to book          |
  | `customerName`  | String | Yes      | Name of the guest                       |
  | `checkIn`       | String | Yes      | Date (YYYY-MM-DD), today or future      |
  | `checkOut`      | String | Yes      | Date (YYYY-MM-DD), after `checkIn`      |

- **Responses**:
  - `201 Created` – JSON `BookingDTO`

**Example Request**:
```json
{
  "roomId": 1,
  "customerName": "John Doe",
  "checkIn": "2025-05-01",
  "checkOut": "2025-05-05"
}
```

**Example Response**:
```json
{
  "id": 10,
  "customerName": "John Doe",
  "checkIn": "2025-05-01",
  "checkOut": "2025-05-05",
  "status": "CONFIRMED",
  "room": {
    "id": 1,
    "roomNumber": "101A",
    "capacity": 2,
    "pricePerNight": "99.99",
    "available": false
  }
}
```

#### PUT `/bookings/{id}/cancel`

- **Path Parameter**:
  - `id` (Long): identifier of the booking to cancel
- **Responses**:
  - `200 OK` – JSON `BookingDTO` with `status` updated to `CANCELLED`

**Example Response**:
```json
{
  "id": 10,
  "customerName": "John Doe",
  "checkIn": "2025-05-01",
  "checkOut": "2025-05-05",
  "status": "CANCELLED",
  "room": {
    "id": 1,
    "roomNumber": "101A",
    "capacity": 2,
    "pricePerNight": "99.99",
    "available": true
  }
}
```

#### GET `/bookings/{id}`

- **Path Parameter**:
  - `id` (Long): identifier of the booking to retrieve
- **Responses**:
  - `200 OK` – JSON `BookingDTO`

**Example Response**:
```json
{
  "id": 10,
  "customerName": "John Doe",
  "checkIn": "2025-05-01",
  "checkOut": "2025-05-05",
  "status": "CONFIRMED",
  "room": { ... }
}
```

---

## Example curl Commands

```bash
# List all available rooms
curl -X GET "http://localhost:8080/rooms?available=true" -H "Accept: application/json"

# Create a new room
curl -X POST "http://localhost:8080/rooms" -H "Content-Type: application/json" -d '{
  "roomNumber": "202B",
  "capacity": 3,
  "pricePerNight": "150.00",
  "available": true
}'

# Make a booking
curl -X POST "http://localhost:8080/bookings" -H "Content-Type: application/json" -d '{
  "roomId": 1,
  "customerName": "John Doe",
  "checkIn": "2025-05-01",
  "checkOut": "2025-05-05"
}'

# Cancel a booking
curl -X PUT "http://localhost:8080/bookings/10/cancel" -H "Accept: application/json"

# Get booking details
curl -X GET "http://localhost:8080/bookings/10" -H "Accept: application/json"
```

## Error Handling

All errors return an `ApiError` JSON object:

```json
{
  "status": "BAD_REQUEST",  
  "message": "Detailed error message"
}
```

- **400 Bad Request**: validation failures or business rule violations (e.g., check-in in past, room unavailable)
- **404 Not Found**: room or booking not found
- **500 Internal Server Error**: unexpected errors

---

## Data Types

| Field             | Type     | JSON Representation              |
| ----------------- | -------- | -------------------------------- |
| `id`              | Long     | number (e.g. `1`)                |
| `roomId`          | Long     | number                           |
| `roomNumber`      | String   | string (e.g. "101A")           |
| `capacity`        | int      | number (e.g. `2`)                |
| `pricePerNight`   | BigDecimal | string (e.g. "120.50")       |
| `available`       | boolean  | boolean (`true`/`false`)         |
| `customerName`    | String   | string                           |
| `checkIn`, `checkOut` | LocalDate | string in `YYYY-MM-DD` format |
| `status`          | enum     | string (`"CONFIRMED"`, `"CANCELLED"`)

---

## Swagger / OpenAPI UI

Interactive API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

