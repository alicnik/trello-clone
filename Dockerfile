FROM ibm-semeru-runtimes:open-17-jre-focal
EXPOSE 8080
COPY target/trello-clone-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]