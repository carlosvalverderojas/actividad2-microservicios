# Imagen base con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copiamos el JAR generado por Maven
COPY target/*.jar app.jar

# Render usa PORT dinámico. Local seguirá usando 8080 si no hay PORT.
ENV PORT=8080
EXPOSE 8080

# Arranque del gateway usando el puerto dinámico
ENTRYPOINT ["sh","-c","java -jar app.jar --server.port=${PORT}"]
