# back
# устанавливаем самую лёгкую версию JVM
FROM amazoncorretto:17

LABEL maintainer="2262288@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=./target/WorkerService-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} backend.jar

# команда запуска джарника
ENTRYPOINT ["java", "-jar", "backend.jar"]