# Usamos Java 17
FROM eclipse-temurin:17-jdk-alpine

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR generado por Maven
COPY target/*.jar app.jar

# Puerto del microservicio cliente
EXPOSE 8081

# Comando para arrancar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]
