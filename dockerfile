FROM ubuntu:lastet AS build
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN apt-get install maven -y
RUN mvn clean install
EXPOSE 8080
COPY --from=build /target/ToDoList-1.0.0.jar app.jar
RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN apt-get install maven -y
RUN mvn clean install
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/ToDoList-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]