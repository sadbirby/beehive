# Use an official Maven image to build the application
FROM maven:3.9-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean install -DskipTests

# Use an official OpenJDK runtime as a parent image for the final stage
FROM eclipse-temurin:21-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/beehive-backend-0.0.1-SNAPSHOT.jar .

# Expose the port the application runs on
EXPOSE 8097

# Run the JAR file
ENTRYPOINT ["java", "-jar", "beehive-backend-0.0.1-SNAPSHOT.jar"]
