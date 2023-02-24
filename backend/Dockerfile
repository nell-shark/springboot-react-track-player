# Build stage
FROM gradle:7.6.0-jdk19 AS BUILD_STAGE

WORKDIR /backend

COPY build.gradle settings.gradle ./

COPY gradle ./gradle

RUN gradle clean build || true

COPY . ./

RUN gradle clean bootJar

# Run stage
FROM openjdk:21-oracle

WORKDIR /backend

COPY --from=BUILD_STAGE /backend/build/libs/*.jar ./spring-boot-application.jar

ENTRYPOINT ["java", "-jar", "spring-boot-application.jar"]
