FROM openjdk:8
WORKDIR /app
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
RUN chmod +x ./mvnw
RUN ./mvnw package
COPY /target/*jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]