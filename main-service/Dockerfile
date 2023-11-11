FROM gradle:8.3.0-jdk17-alpine AS build

WORKDIR /main-service
COPY main-service/build.gradle .
COPY main-service/settings.gradle .
COPY main-service/src ./src
RUN gradle bootJar --no-daemon

FROM openjdk:17-alpine
ARG JAR_FILE=/main-service/build/libs/*.jar
COPY --from=build $JAR_FILE /opt/main-service/app.jar
ENTRYPOINT ["java", "-jar", "/opt/main-service/app.jar"]
