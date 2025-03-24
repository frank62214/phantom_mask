FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar demo-0.0.1-SNAPSHOT.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]