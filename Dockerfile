COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


# -------- STAGE 2: Run --------
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/target/MotherLink2-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]