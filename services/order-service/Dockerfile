# oredr-service Dockerfile
FROM eclipse-temurin:23-jdk-alpine

WORKDIR /app

COPY target/order-service-0.0.1-SNAPSHOT.jar order-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "order-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080