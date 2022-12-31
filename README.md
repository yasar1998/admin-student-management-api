# Admin student management API
## Project Description
### Role based actions
- Admin can see all users, student users, specific student user, delete any user, give role to specific user, view his/her profile, change his/her password.
- Student can only view his/her profile, change his/her password.
### Validation
- Admin registration is impossible since this role must be assigned by any other admin user.
- Password, first name, last name, username must not be blank and username must be unique during registration
- Username and password must not be blank during login
- During password change, provided passwords must not be empty.
- During role assignment to specific user, username must not be blank

## Test and Run with Maven
1. To run the project: *mvn spring-boot:run*
2. To run the tests: *mvn clean test*

## Run with Docker (Docker Compose)
Step 1. mvn clean package (clean JAR creation)
Step 2. docker-compose up


## Technologies
- Spring Boot
- Spring Web (REST)
- Spring Security (JWT based)
- Spring Data JPA
- H2 Database
- Hibernate Validator
- Jackson
- Lombok
- Swagger API
- Slf4j logger
- Unit Tests with JUnit and Mockito
- Integration Tests with MockMvc

### REST endpoints
- REST endpoints can be tested with Postman.
- To review REST endpoints' documentation
  - */swagger-ui/*  (visualize and interact)
  - */v2/api-docs* (visualize)

### Note
It is better to have one admin user in the system during program startup to be able to check the endpoints since admin registration is impossible (it can only be assigned by admin user in the system)

Admin User Credentials:
- username: yashar
- password: password
