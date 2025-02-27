FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/HR-Service-1.0-SNAPSHOT.war app.jar
ENTRYPOINT ["java","-jar","/app.jar"]