# currency-service Dockerfile
FROM eclipse-temurin:23-jdk-alpine

WORKDIR /app

COPY target/currency-service-0.0.1-SNAPSHOT.jar currency-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "currency-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080