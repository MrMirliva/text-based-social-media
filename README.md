# Text-Based Social Media

This project is a **terminal-based social media application** developed as part of the **CME2210 - Object-Oriented Analysis and Design** course. It was built collaboratively by a team of three students, including myself.

## 📌 Project Objective

The goal of this project is to simulate the fundamental features of a social media platform (such as user registration, authentication, posting, liking, following, and profile management) via a **command-line interface** (CLI). The application serves as a platform to apply **object-oriented programming (OOP)** principles and implement a clean, layered software architecture without relying on any external frameworks.

## 🚀 Features

- **User Registration & Login**
- **Profile Management** (view/update username & password)
- **Create, Edit, and Delete Posts**
- **Like and Unlike Posts**
- **Follow / Unfollow Users**
- **View Personalized Feed and Global Timeline**
- **View Followers and Following Counts**
- **All data is persistently stored using encrypted local text files**

## 🧱 System Architecture

The application follows a **modular layered architecture** consisting of:

- **Interface Layer**: Menu-driven CLI using standard input/output.
- **Service Layer**: Handles business logic and returns standardized responses using `ResponseEntity<T>`.
- **Repository Layer**: Provides abstracted file I/O through a custom `MACRepository<T>`.
- **Model Layer**: Defines data entities (`User`, `Post`, `Like`, `Follow`) via an abstract `MACModel`.
- **Helper Layer**: Utility tools like encryption (`CryptoUtil`), random ID generation, date utilities.
- **DTO Layer**: Typed request and response classes to encapsulate data between layers.

> The architecture emphasizes **single responsibility**, **testability**, and **ease of future enhancements**, such as GUI or database migration.

## 🔐 Data Security

- **Passwords** are encrypted using a custom `CryptoUtil` module.
- **Data Integrity** is preserved through unique IDs and timestamps for all entities.
- File operations (serialization & deserialization) are handled transparently and securely.

## ⚙️ Requirements

- **Java Version**: 21
- **Operating System**: Any OS with command-line support (Windows, Linux, or macOS)
- **No external dependencies or frameworks used** (e.g., no Spring, Hibernate, or JavaFX)

## 🖥️ Usage

To run the application:

```bash
javac Main.java
java Main
```

All functionality is navigated through a **menu-based terminal interface**. New users must register before accessing social features.

## 👨‍💻 Contributors

- Abdullah Gündüz https://github.com/MrMirliva
- Ömer Tahsin Taşkın https://github.com/omertahsintaskin
- Muhammed Yasin Eroğlu https://github.com/MuhammedYasinEroglu

Each team member actively contributed to various aspects of the development process including design, coding, and testing.

## 🧪 Testing

Testing was conducted manually through scenario-based interaction. All core functionalities (registration, post interaction, following, etc.) were verified through the command-line interface and inspected via file outputs. The `ResponseEntity<T>` structure enabled standardized error handling and feedback.

## 🛠️ Future Improvements

- Implementing a **Graphical User Interface (GUI)**
- Migrating to **SQLite** or other lightweight databases
- Adding new features: **Comments, Notifications, Messaging**
- Extending **Memento Layer** to support version control or rollback
- Introducing automated testing via **JUnit**

## 📄 License

This project is developed **strictly for educational purposes** within the scope of the CME2210 course. Unauthorized commercial use is prohibited.
