FROM adoptopenjdk/openjdk11:alpine

#CMD ["/bin/sh"]
           # For Java 8, try this
           # FROM openjdk:8-jdk-alpine

           # For Java 11, try this
           #FROM adoptopenjdk/openjdk11:alpine-jre
#rodando como usuário spring no SO
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
# Refer to Maven build -> finalName
ARG JAR_FILE=target/*.jar

# cd /opt/app
#WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]