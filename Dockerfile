FROM maven:latest as build_stage

COPY ./ /build/
WORKDIR /build

RUN mvn install

FROM openjdk:14-alpine

WORKDIR /app
COPY --from=build_stage /build/target/prime-numbers-rest-api.jar /app/

ENTRYPOINT ["java", "-jar", "prime-numbers-rest-api.jar"]
