# Use the official Maven image to build the project
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use the official OpenJDK image to run the application
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/Assignment.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
