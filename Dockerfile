FROM openjdk:17-jdk-slim
COPY target/*.jar books-store.jar
ENTRYPOINT ["java", "-jar", "books-store.jar"]
EXPOSE 8080