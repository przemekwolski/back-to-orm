FROM gradle:8.9.0-jdk21-alpine AS build
COPY --chown=gradle:gradle src /home/gradle/src
COPY --chown=gradle:gradle build.gradle settings.gradle /home/gradle/
WORKDIR /home/gradle
RUN gradle build --no-daemon

FROM openjdk:21-slim-buster
COPY --from=build /home/gradle/build/libs/back-to-orm-0.0.1.jar /app/back-to-orm-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/back-to-orm-0.0.1.jar"]