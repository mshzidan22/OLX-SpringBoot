FROM maven:3.8.6-openjdk-8-slim AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests
FROM openjdk:8-jdk-slim
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
