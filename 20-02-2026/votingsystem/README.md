# 🗳️ VoteSecure - Secure Online Voting System

![Spring Boot](https://img.shields.io/badge/Spring_Boot-F27473?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

VoteSecure is a complete, secure, and user-friendly web application designed to manage digital voting processes. It features user authentication, candidate management, and a robust password recovery system via email.

---

## ✨ Features

*   **🔐 Secure Authentication**: User registration and login with BCrypt password hashing.
*   **📩 Password Recovery**: "Forgot Password" functionality with secure, time-limited tokens sent via Gmail SMTP.
*   **📊 Dashboard**: Elegant user interface for managing candidates and viewing voting status.
*   **🛡️ Data Integrity**: Relational database structure using MySQL to ensure consistent and reliable records.
*   **🎨 Premium UI**: Modern Red & White theme with smooth animations and responsive design.

---

## 🚀 Tech Stack

- **Backend**: Java 17+, Spring Boot 4.0.3, Spring Data JPA
- **Database**: MySQL 8.0+ / H2 (for testing)
- **Frontend**: HTML5, Vanilla CSS (Custom Design), JavaScript
- **Security**: BCrypt Password Hashing
- **Email**: Spring Mail Starter (Gmail SMTP)
- **Utilities**: Lombok, Maven

---

## 🛠️ Installation & Setup

### 1. Prerequisites
Ensure you have the following installed on your laptop:
*   [Java Development Kit (JDK) 17 or 21](https://www.oracle.com/java/technologies/downloads/)
*   [MySQL Server](https://dev.mysql.com/downloads/installer/) (or XAMPP for a quick setup)
*   An IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [VS Code](https://code.visualstudio.com/)

### 2. Database Configuration
By default, the application connects to a database named `voting_system`.
1. Open your MySQL terminal or phpMyAdmin.
2. Run the following command:
   ```sql
   CREATE DATABASE IF NOT EXISTS voting_system;
   ```

### 3. Email Secrets (Required for Password Reset)
The application uses environment variables for sensitive email credentials.
1. Create a file named `src/main/resources/application-local.properties` (this is ignored by Git).
2. Add your credentials:
   ```properties
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-app-password
   ```
   *Note: Use a **Google App Password**, not your regular Gmail password.*

### 4. Running the Application
*   **Via IDE**: Right-click `VotingsystemApplication.java` and select **Run**.
*   **Via Terminal**:
    ```bash
    ./mvnw spring-boot:run
    ```

The application will be available at: **[http://localhost:8080](http://localhost:8080)**

---

## 📁 Project Structure

```text
votingsystem/
├── src/main/java/com/voting/votingsystem/
│   ├── controller/      # Web Controllers
│   ├── entity/          # Database Models
│   ├── repository/      # Data Access Layer
│   ├── service/         # Business Logic
│   └── dto/             # Data Transfer Objects
├── src/main/resources/
│   ├── static/          # CSS, JS, and Images
│   ├── templates/       # HTML Pages
│   └── application.properties
└── pom.xml              # Maven Dependencies
```

---

## 🤝 Contributing
If you are helping out with this project:
1. Fork the project.
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the Branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

## 📄 License
This project is developed for internship purposes.

---
*Created with ❤️ for Secure Voting.*
