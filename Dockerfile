# Use a multi-stage build to build the JAR file in a separate stage and then copy it to a lightweight runtime image

# Build stage
FROM maven:3.9.5-eclipse-temurin-21 AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven pom.xml file first and download dependencies to take advantage of Docker caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# List files in the target directory
RUN ls -la /app/target

# Runtime stage
FROM openjdk:17-jdk-slim

# Volume and port
EXPOSE 8080
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0"

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*-runner.jar /app.jar

# Set the command to run the JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]
