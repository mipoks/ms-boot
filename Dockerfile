#FROM openjdk:11
#WORKDIR /app
#ARG JAR_FILE=target/ms-boot-0.0.2.jar
#COPY ${JAR_FILE} /app/app.jar
#ENV DB_HOST=localhost
#ENV DB_PORT=5432
#ENV DB_DATABASE=rtgrthb
#CMD java -jar app.jar
##ENTRYPOINT ["java -jar /app.jar"]
#
##FROM adoptopenjdk:11-jdk
##WORKDIR /app
##COPY target/krst-projects-1.0.0.jar /app/
##ENV DB_HOST=3425\
##DB_PORT=2354 \
##DB_DATABASE=rtgrthb
##CMD java -jar krst-projects-1.0.0.jar

FROM openjdk:11
ARG JAR_FILE=ms-boot/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]