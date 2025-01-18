FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/device-manager-app.jar app.jar

EXPOSE 8080

ENV DB_URL=jdbc:postgresql://localhost:5432/device_management_db
ENV DB_USER=postgres
ENV DB_PASSWORD=1234567890

ENTRYPOINT ["java", "-jar", "app.jar"]
