FROM maven:3.6.3-jdk-8-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml package -DskipTests

#
# Package stage
#
FROM maven:3.6.3-jdk-8-slim
COPY --from=build /home/app/target/olx-0.0.1-SNAPSHOT.jar  /usr/local/lib/olx.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/olx.jar"]
