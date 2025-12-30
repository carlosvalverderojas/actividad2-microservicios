# Imagen base con Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copiamos el JAR generado por Maven
COPY target/*.jar app.jar

# Puerto del gateway
EXPOSE 8080

# Arranque del gateway
ENTRYPOINT ["java", "-jar", "app.jar"]
