# ✔️ RememberMe API (Backend for a full-stack application)

RememberMe API is the backend of a full-stack task-management application currently being developed with Spring Boot (backend) and Angular (frontend).
The goal of the system is to help users organize their personal tasks while also providing smart reminders through email and other notification mechanisms (coming soon).

This API handles:
- Secure user authentication using JWT
- Task creation, edition, deletion, and retrieval
- Isolation of user data (each user only accesses their own tasks)
- Database migrations via Flyway

Future planned capabilities include:<br>
📧 Automated email reminders based on task due dates<br>
🔔 Push notifications for upcoming tasks<br>
🗓️ Calendar-based task visualization<br>
👤 User settings and customization<br>

This project is part of my portfolio and demonstrates my ability to design and build professional backend architectures.

## Front-end in Development
<img width="1440" height="1024" alt="LoginPage" src="https://github.com/user-attachments/assets/aa1165ab-fdde-4c93-9ccf-a2e970594438" />


## Technologies Used

- Java 21
- Spring Boot 3
- Spring Web
- Spring Security (JWT)
- Spring Data JPA
- MySQL
- Flyway
- Lombok

## Features

- User registration and authentication (JWT)<br>
- Stateless API using Bearer tokens<br>
- Create / update / delete personal tasks<br>
- Each user only sees their own tasks<br>
- Role system prepared (USER / ADMIN)<br>
- Clean DTO separation (request & response)<br>
- Database versioning with Flyway<br>
- Ready to connect to an Angular front-end<br>

## Project Structure
```bash
src/main/java/com/zavattieri/RememberMe
 ├── controller                             # REST controllers
 ├── domain                                 # Entities (User, Task)
 ├── dto                                    # Request/response DTOs
 ├── repository                             # Spring Data repositories
 ├── security                               # JWT, filter, config
 ├── service                                # Business logic
 └── RememberMeApplication.java
```

## Installation
1. Clone the repository
```
git clone https://github.com/USERNAME/rememberme-api.git
cd rememberme-api
```
<br>

2. Create the MySQL database
```
CREATE DATABASE rememberme;
```
<br>

3. Create your application.properties
```
spring.application.name=RememberMe
server.port=8080

# JWT secret (comes from an environment variable)
api.security.jwt.secret=${JWT_SECRET}

# MySQL database connection
spring.datasource.url=jdbc:mysql://localhost:3306/rememberme
spring.datasource.username=root
spring.datasource.password=

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

# Flyway migrations
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```
<br>

4. Set the JWT environment variable

Windows (PowerShell):
  ```
  setx JWT_SECRET "your_super_secret_key"
  ```
macOS/Linux:
  ```
  export JWT_SECRET="your_super_secret_key"
  ```
<br>

5. Run the application
```
mvn spring-boot:run
```

## Testing with Insomnia/Postman

Authentication Endpoints:
```
POST /auth/register
POST /auth/login
```
Successful login returns:
```
{
	"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJSZW1lbWJlck1lIEFQSSIsInN1YiI6ImN6LnJlbWVtYmVyLm1lQGdtYWlsLmNvbSIsImV4cCI6MTc2NDkzNTQ1MX0.R9mGIAqbvaMx6tC9G6dJTdOChfNAUO2tD_GsiMdqUq0",
	"name": "Carmem Zava",
	"email": "cz.remember.me@gmail.com",
	"role": "USER"
}
```
Use the token in all protected routes:
```
Authorization: Bearer <token>
```

Task Endpoints (Protected):
| Method | Route              | Description       |
| ------ | ------------------ | ----------------- |
| GET    | /tasks             | List user's tasks |
| GET    | /tasks/{id}        | Get task by ID    |
| POST   | /tasks/create      | Create a task     |
| PUT    | /tasks/update/{id} | Update a task     |
| DELETE | /tasks/delete/{id} | Delete a task     |

Example body (create):
```
{
	"title":"Christmas",
	"description":"Dinner with friends",
	"taskCategory":"FAMILY",
	"dueDate":"2025-12-06T18:30:00"
}
```

## 👊 Contributing
Pull requests are welcome.<br>
Please fork the project, create a branch, apply your changes, and submit a PR.

## 👤 Author
Carmem Zavattieri<br>
Spring Boot • Angular • Full-stack development
