# ====== STAGE 1: BUILD (compila el JAR) ======
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos primero el pom para aprovechar cache
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copiamos el código y compilamos
COPY src ./src
RUN mvn -DskipTests clean package

# ====== STAGE 2: RUN (ejecuta el JAR) ======
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
