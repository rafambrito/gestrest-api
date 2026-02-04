# Etapa 1: Build do projeto com Maven
FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /app

ENV MAVEN_OPTS="-Djava.io.tmpdir=/tmp"

# Cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build do projeto
COPY src ./src
RUN mvn clean package -DskipTests


# Etapa 2: Runtime

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/target/gestrest-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
