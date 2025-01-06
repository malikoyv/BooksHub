# 📚 Book Management System

The **Book Management System** is a full-stack application that allows users to manage authors, books, and associated data seamlessly. It features robust backend services, a scalable architecture, and a clean frontend for managing a book database.

---

## 🌟 Features

- **Author Management**:
  - Add, edit, and delete author information.
  - Fetch authors from OpenLibrary API.
  - Track alternate names, work counts, top subjects, and dates of birth/death.

- **Book Management**:
  - Manage books with full metadata.
  - Scheduled books update from OpenLibrary.
  - Scheduled books' ratings update from OpenLibrary.

- **Caching for Performance**:
  - Implemented caching for better performance.

- **Testing**:
  - Comprehensive **unit tests** for services.
  - **Selenium tests** for frontend validation.

- **Frontend**:
  - Interactive and responsive design for seamless user experience.

---

## 🛠️ Project Structure

```plaintext
📁 .idea/             # Project settings for IntelliJ IDEA
📁 api/               # REST API endpoints
📁 client/            # Java client for API interactions
📁 core/              # Core business logic and entities
📁 frontend/          # Frontend implementation
📁 gradle/wrapper/    # Gradle build files
.gradlew              # Gradle wrapper script (Unix)
.gradlew.bat          # Gradle wrapper script (Windows)
build.gradle          # Gradle build configuration
settings.gradle       # Gradle project settings
```

---

## 🚀 Quick Start

### Prerequisites

- **Java 20+**
- **Gradle** (or use the provided Gradle wrapper)
- **MySQL/MariaDB**

---

### 1️⃣ Backend Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/malikoyv/BooksHub
   cd BooksHub
   ```

2. Build and run the backend:
   ```bash
   ./gradlew bootRun
   ```

3. Configure your database connection in `application.properties`.

---

### 3️⃣ Run Tests

- **Unit Tests**:
  ```bash
  ./gradlew test
  ```

- **Selenium Tests**:
  Ensure the frontend server is running, then execute:
  ```bash
  ./gradlew seleniumTest
  ```

---

## 🧪 Technologies Used

- **Backend**:
  - Spring Boot, Java 20
  - Hibernate, JPA
  - REST APIs
- **Frontend**:
  - Thymeleaf
- **Database**:
  - MariaDB
- **Testing**:
  - JUnit, Mockito
  - Selenium for end-to-end tests
- **Build Tool**:
  - Gradle

---

## 🤝 Contribution Guidelines

1. Fork the repository.
2. Create a new feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature description"
   ```
4. Push the branch and create a pull request:
   ```bash
   git push origin feature-name
   ```

---

## ✨ Acknowledgments

Special thanks to all contributors and libraries that made this project possible!
