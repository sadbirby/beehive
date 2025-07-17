# Beehive

Beehive is a forum-like social media application that allows users to create posts, comment on posts, and like posts. It is built with a modern tech stack, featuring a Spring Boot backend and a React frontend.

## About The Project

This project is a full-stack web application designed to provide a seamless and interactive user experience. The backend is built with Java and Spring Boot, providing a robust and scalable REST API. The frontend is a single-page application built with React, offering a dynamic and responsive user interface.

### Built With

This project is built with a variety of modern technologies and frameworks, including:

**Backend:**

*   [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
*   [Spring Boot 3](https://spring.io/projects/spring-boot)
*   [Spring Security](https://spring.io/projects/spring-security)
*   [JPA (Java Persistence API)](https://www.oracle.com/java/technologies/jpa.html)
*   [MySQL](https://www.mysql.com/)
*   [Maven](https://maven.apache.org/)

**Frontend:**

*   [React 18](https://reactjs.org/)
*   [Vite](https://vitejs.dev/)
*   [shadcn/ui](https://ui.shadcn.com/)
*   [Tailwind CSS](https://tailwindcss.com/)
*   [React Router](https://reactrouter.com/)
*   [Axios](https://axios-http.com/)

## Getting Started

To get a local copy up and running follow these simple example steps.

### Prerequisites

Before you begin, ensure you have the following software installed on your system:

*   **Java Development Kit (JDK) 21 or later:** Required for the backend.
*   **Node.js 20.15.1 or later:** Required for the frontend.
*   **MySQL 8.0.32 or later:** The database for the application.

### Installation

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/your_username/beehive.git
    ```
2.  **Backend Setup:**
    *   Navigate to the `beehive-backend` directory.
    *   Create a `.env` file and add the following environment variables:
        ```
        DB_USER=your_db_username
        DB_PASSWORD=your_db_password
        JWT_SIGNING_KEY=your_jwt_signing_key
        ```
    *   Run the application using Maven:
        ```sh
        ./mvnw spring-boot:run
        ```
3.  **Frontend Setup:**
    *   Navigate to the `beehive-frontend` directory.
    *   Install the dependencies:
        ```sh
        npm install
        ```
    *   Start the development server:
        ```sh
        npm run dev
        ```

## Usage

Once both the backend and frontend servers are running, you can access the application in your web browser at `http://localhost:5173`.

The backend API documentation is available at `http://localhost:8097/swagger-ui/index.html`.