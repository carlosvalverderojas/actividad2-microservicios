# ====== BUILD (compila el jar dentro del contenedor) ======
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos wrapper y pom primero (mejor cache)
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
COPY .mvn .mvn
COPY pom.xml pom.xml

# Descarga dependencias (cache)
RUN ./mvnw -DskipTests dependency:go-offline

# Copiamos el código y compilamos
COPY src src
RUN ./mvnw -DskipTests package

# ====== RUNTIME ======
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Render usa PORT dinámico. Local seguirá usando 8081 si no hay PORT.
ENV PORT=8081
EXPOSE 8081

ENTRYPOINT ["sh","-c","java -jar app.jar --server.port=${PORT}"]
