FROM gradle:8.3.0-jdk17-alpine AS build

WORKDIR /user-service
COPY user-service/build.gradle .
COPY user-service/settings.gradle .
COPY user-service/src ./src
RUN gradle bootJar --no-daemon

FROM openjdk:17-alpine
ARG JAR_FILE=/user-service/build/libs/*.jar
COPY --from=build $JAR_FILE /opt/user-service/app.jar
ENTRYPOINT ["java", "-jar", "/opt/user-service/app.jar"]
