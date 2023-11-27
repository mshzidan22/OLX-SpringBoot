FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests
FROM openjdk:11-jdk-slim
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
