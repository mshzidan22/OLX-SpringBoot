FROM openjdk:8 as buildstage
WORKDIR /app
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
RUN chmod +x ./mvnw
RUN ./mvnw package
COPY target/*jar app.jar
FROM openjdk:8
COPY --from=buildstage /app/app.jar .
ENTRYPOINT ["java","-jar","/app.jar"]
