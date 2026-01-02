# ====== BUILD ======
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos el pom primero para cache
COPY pom.xml .
RUN mvn -DskipTests dependency:go-offline

# Copiamos el código y compilamos
COPY src src
RUN mvn -DskipTests package

# ====== RUNTIME ======
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Render asigna el puerto dinámicamente (local usa 8761)
ENV PORT=8761
EXPOSE 8761

ENTRYPOINT ["sh","-c","java -jar app.jar --server.port=${PORT}"]
