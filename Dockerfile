FROM maven:3.6.3-jdk-8
WORKDIR /app
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
RUN mvn package clean package
COPY /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]