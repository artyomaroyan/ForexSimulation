# customer-service Dockerfile
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY target/customer-service-0.0.1-SNAPSHOT.jar app.jar

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080