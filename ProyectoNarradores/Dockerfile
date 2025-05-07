# Dockerfile
FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/Proyecto-Narradores-0.0.1-SNAPSHOT.jar
VOLUME /tmp
COPY ${JAR_FILE} Proyecto-Narradores.jar
ENTRYPOINT ["java","-jar","/Proyecto-Narradores.jar"]
