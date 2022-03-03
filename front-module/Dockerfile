FROM openjdk:11
ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app2.jar
ENTRYPOINT ["java","-jar","/app2.jar"]