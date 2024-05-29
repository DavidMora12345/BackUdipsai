# Usa Amazon Corretto JDK 21 con Alpine Linux como imagen base
FROM amazoncorretto:21-alpine-jdk

# Copia el archivo JAR generado al contenedor
COPY target/TUdipsaiApi-0.0.1-SNAPSHOT.jar app.jar

# Configura el punto de entrada para ejecutar la aplicación JAR
ENTRYPOINT ["java","-jar","/app.jar"]
