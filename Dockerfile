# Use the official OpenJDK 11 base image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/*.jar /app/app.jar

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Command to run on container start
CMD ["java", "-jar", "app.jar"]
