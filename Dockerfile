# Usa Amazon Corretto JDK 21 con Alpine Linux como imagen base
FROM amazoncorretto:21-alpine-jdk

# Instala Maven en el contenedor
RUN apk add --no-cache maven

# Crea un directorio para la aplicación
WORKDIR /app

# Copia el archivo pom.xml y descarga las dependencias del proyecto
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el código fuente del proyecto al contenedor
COPY src ./src

# Construye el proyecto
RUN mvn clean package -DskipTests

# Copia el archivo JAR generado al contenedor
COPY target/TUdipsaiApi-0.0.1-SNAPSHOT.jar app.jar

# Configura el punto de entrada para ejecutar la aplicación JAR
ENTRYPOINT ["java","-jar","/app.jar"]
