# Device Management Application

A CRUD application for managing devices with state transitions and approval workflows.

## Domain Model

Each device contains the following attributes:

- **Id**: Unique identifier for the device.
- **Name**: Name of the device.
- **Brand**: Device brand.
- **State**: Current state of the device (`AVAILABLE`, `IN_USE`, `INACTIVE`).
- **Creation Time**: Timestamp of device creation.

## Device State Transition

### State Transition Rules

1. **AVAILABLE**:
    - Can transition to `IN_USE` or `INACTIVE`.

2. **IN_USE**:
    - Can transition back to `AVAILABLE`.
    - Cannot transition directly to `INACTIVE` (requires transitioning to `AVAILABLE` first).

3. **INACTIVE**:
    - Can transition to `IN_USE` or `AVAILABLE` after an activation request approval.

### API Endpoints for State Transition

- **Transition `AVAILABLE` to `IN_USE` or `INACTIVE`:**  
  `PATCH /devices/{id}/state/{state}`  
  [API Documentation](http://localhost:8080/api/swagger-ui/index.html#/device-controller/changeState)

- **Transition `IN_USE` to `AVAILABLE`:**  
  `PATCH /devices/{id}/state/{state}`  
  [API Documentation](http://localhost:8080/api/swagger-ui/index.html#/device-controller/changeState)

- **Transition `INACTIVE` to `IN_USE` or `AVAILABLE`:**
    1. Create an activation request:
       `POST /devices/{id}/approval-requests`  
       [API Documentation](http://localhost:8080/api/swagger-ui/index.html#/device-controller/createDeviceStateRequest)
    2. Check approval request status:
       `GET /devices/{id}/approval-requests`  
       [API Documentation](http://localhost:8080/api/swagger-ui/index.html#/device-controller/listStatesRequests)
    3. Approve the request:
       `PUT /devices/approval-requests/{approval-request-id}`  
       [API Documentation](http://localhost:8080/api/swagger-ui/index.html#/device-controller/approveRequest)

## Accessing API Documentation

The complete API documentation is available at:  
[Swagger UI](http://localhost:8080/api/swagger-ui/index.html)

## Running the Application

### Prerequisites

- Java 21
- Maven
- Docker

### Steps

1. Build the application:
   ```bash
   mvn clean package

2. Start the application using Docker Compose:
   ```bash
   docker compose up

### Technologies Used
* Spring Boot: Backend framework.
* Java 21: Programming language.
* Maven: Dependency and build management.
* Docker: Containerization.
* OpenAPI docs: API documentation.
* Lombok: Simplified Java model creation.
* Flyway: SQL scripts.
* Postgres: Database.

## Notes for Assessment

- The application is designed to demonstrate clean code principles and adherence to best practices.
- Focus on the validation logic for state transitions, as it highlights the application's business rules.
- Swagger UI provides detailed information on API endpoints for testing and exploration.