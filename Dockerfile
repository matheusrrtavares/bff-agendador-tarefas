FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTest

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/*.jar app.jar
EXPOSE 8083
CMD ["java", "-jar", "/app/bff-agendador-tarefas.jar"]