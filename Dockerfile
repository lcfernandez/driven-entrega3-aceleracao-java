# Stage of building
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw && ./mvnw clean install -DskipTests

# Stage of execution
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar api.jar
EXPOSE 8080
CMD ["java", "-jar", "api.jar"]