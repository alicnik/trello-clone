FROM openjdk:17
EXPOSE 8080
COPY target/trello-clone-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]