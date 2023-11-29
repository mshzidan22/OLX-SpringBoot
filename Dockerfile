FROM maven:3.9.5-jdk-8 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests
FROM openjdk:8-jdk-slim
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
