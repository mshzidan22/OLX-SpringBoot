FROM adoptopenjdk:8
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} olx-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "olx-0.0.1-SNAPSHOT.jar"]
