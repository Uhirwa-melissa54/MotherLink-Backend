# Use official Java runtime
FROM eclipse-temurin:17-jdk

# Set working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/MotherLink2-0.0.1-SNAPSHOT.jar app.jar
COPY .env .env

# Expose the port your app uses
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
