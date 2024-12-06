# EV Charging Station Management System

This is a Spring Boot application for managing EV Charging Stations.

## Features

- User authentication and authorization
- Charging Station management (CRUD operations)
- Connector type management (CRUD operations)
- Charging Station status management (CRUD operations)
- User profile management (CRUD operations)
- Otp verification for login
- Bulk Upload and Dowload excel files
- API Aggregator - Using https://api.openchargemap.io/v3/poi/

## Technologies Used

- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Maven
- Docker

## How to Run

- Clone the repository

### Without docker
- create mysql db of name `evcsmsdb`
- Run `mvn spring-boot:run` in the project directory

### with docker
- docker-compose down
- docker-compose up --build

## Swagger Url

http://localhost:8080/swagger-ui/index.html

## API Endpoints

- GET /charging-stations: Get all charging stations
- POST /charging-stations: Create a new charging station
- GET /charging-stations/{id}: Get a charging station by id
- PUT /charging-stations/{id}: Update a charging station
- DELETE /charging-stations/{id}: Delete a charging station
- GET /connector-types: Get all connector types
- POST /connector-types: Create a new connector type
- GET /connector-types/{id}: Get a connector type by id
- PUT /connector-types/{id}: Update a connector type
- DELETE /connector-types/{id}: Delete a connector type
- GET /users: Get all users
- POST /users: Create a new user
- GET /users/{id}: Get a user by id
- PUT /users/{id}: Update a user
- DELETE /users/{id}: Delete a user
- POST /login: Login with username and password
- POST /otp: Generate and send otp to user's phone number
- POST /verify-otp: Verify otp

# link to setup Server
https://chatgpt.com/share/670a7615-68a0-800f-b411-e8d4b4808889
