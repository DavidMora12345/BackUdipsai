# Usa Amazon Corretto JDK 21 con Alpine Linux como imagen base
FROM amazoncorretto:21-alpine-jdk


COPY target/TUdipsaiApi-0.0.1-SNAPSHOT.jar app.jar


ENTRYPOINT ["java","-jar","/app.jar"]
