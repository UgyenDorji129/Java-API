FROM openjdk:17.0.1-jdk-slim as build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw install

ENTRYPOINT ["java","-jar","/app/target/socialmediaplatform-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
