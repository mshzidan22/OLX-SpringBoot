########Maven build stage########
FROM maven:3.6-jdk-11 as maven_build
WORKDIR /app

#copy pom
COPY pom.xml .

#copy source
COPY src ./src

# build the app and download dependencies only when these are new (thanks to the cache)
RUN --mount=type=cache,target=/root/.m2  mvn clean package -Dmaven.test.skip

# split the built app into multiple layers to improve layer rebuild
RUN mkdir -p target/docker-packaging && cd target/docker-packaging && jar -xf ../my-app*.jar




FROM gcr.io/distroless/java
COPY --from=build /usr/src/app/target/olx-0.0.1-SNAPSHOT.jar /usr/app/helloworld-1.0.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/olx-0.0.1-SNAPSHOT.jar"]