# Use an official Maven image to build the Spring Boot app
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Copy .env file into the container
COPY .env /app/.env


# Use an official OpenJDK image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/Propert-Rental-System-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8081
EXPOSE 8081

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
