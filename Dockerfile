# Build stage
FROM gradle:9.7.1-jdk21 AS builder
WORKDIR /workspace

COPY build.gradle settings.gradle gradle.properties ./
COPY src ./src

RUN gradle clean bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /workspace/build/libs/gradleboard.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
