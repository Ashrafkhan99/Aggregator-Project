# Aggregator-Project

# Aggregator Application

The Aggregator Application is a Spring Boot-based project that provides a personalized news aggregation experience. It allows users to register, set preferences, fetch news articles, and save their favorite articles securely.

---

## Features

### **User Management**
- **Registration**: Users can register and activate their accounts via a token-based verification system.
- **Authentication**: Secure login using JWT (JSON Web Token) for session management.
- **Role Management**: Supports roles (e.g., admin, user) for access control.

### **News Aggregation**
- **Personalized News**: Fetches news articles based on user-defined preferences (keywords).
- **Favorites**: Users can save specific articles as favorites for easy access later.
- **External API Integration**: Integrates with the `newsapi.org` API to fetch real-time news.

### **Security**
- **JWT Authentication**: Secures endpoints with token-based authentication.
- **Password Encryption**: Uses `BCryptPasswordEncoder` for secure password storage.
- **Custom Security Filters**: Implements a `JwtFilter` to validate tokens for protected endpoints.

---

## Endpoints

### **Public Endpoints**
- `POST /register`: Register a new user.
- `POST /verifyToken`: Verify the activation token to activate the user account.
- `POST /signin`: Authenticate and receive a JWT token.

### **Protected Endpoints**
- `GET /hello`: Test endpoint for authenticated users.
- `GET /tokenHello`: Another test endpoint requiring a valid token.
- `GET /preferences`: Fetch user preferences.
- `POST /preferences`: Update user preferences.
- `GET /news`: Fetch personalized news articles.
- `POST /User/{userId}/favorites`: Save favorite articles by author and date.

---

## Technologies Used

- **Java 17**
- **Spring Boot 3.4.5**
  - Spring Security
  - Spring Data JPA
  - Spring Web
- **MySQL**: Database for storing user, preferences, and favorites data.
- **H2 Database**: In-memory database for testing.
- **JWT**: Token-based authentication.
- **Maven**: Dependency management and build tool.

---

## Project Structure

### **Configuration**
- `SecurityConfig.java`: Configures Spring Security, including JWT filters and endpoint access rules.
- `JwtFilter.java`: Custom filter to validate JWT tokens for protected endpoints.

### **Controllers**
- `UserController.java`: Handles user registration, login, and token verification.
- `NewsController.java`: Manages fetching news articles and saving favorites.

### **Services**
- `UserServices.java`: Implements user-related business logic, including registration, login, and token validation.
- `NewsService.java`: Handles fetching news articles from the external API and saving favorites.
- `PreferenceService.java`: Manages user preferences for personalized news.

### **Entities**
- `User.java`: Represents the user entity with fields like name, role, password, preferences, and favorites.
- `Preference.java`: Represents user-defined keywords for news personalization.
- `Favorite.java`: Represents saved favorite articles for a user.
- `VerificationToken.java`: Represents tokens used for user account activation.

### **Repositories**
- `UserRepository.java`: Manages user data in the database.
- `PreferenceRepository.java`: Handles CRUD operations for user preferences.
- `FavoriteRepository.java`: Manages favorite articles for users.
- `VerificationTokenRepository.java`: Handles token storage and validation.

### **Utilities**
- `JwtUtil.java`: Utility class for generating, validating, and extracting data from JWT tokens.

---
