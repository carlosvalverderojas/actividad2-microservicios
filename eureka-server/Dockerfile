FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Render asigna el puerto dinámicamente
ENV PORT=8761
EXPOSE 8761

ENTRYPOINT ["sh","-c","java -jar app.jar --server.port=${PORT}"]
