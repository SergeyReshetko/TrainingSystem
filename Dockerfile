FROM maven:3.9.9-eclipse-temurin-17 AS build
LABEL authors="Sergey"

WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /build/target/*.jar app.jar

RUN addgroup -S appgroup && \
    adduser -S -G appgroup appuser && \
    chown appuser:appgroup /app/app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--spring.profiles.active=default"]