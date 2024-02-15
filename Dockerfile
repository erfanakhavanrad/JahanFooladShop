FROM openjdk:21
ARG JAR_FILE=target/*.jar
#ARG WAR_FILE=target/*.war
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
