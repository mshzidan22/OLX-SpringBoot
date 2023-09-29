FROM maven:3.6.3-jdk-8
WORKDIR /app
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn
COPY src src
RUN mvn package clean package
COPY target/${JAR_FILE} /usr/share/${JAR_FILE}
ENTRYPOINT ["java","-jar","/app.jar"]