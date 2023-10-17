FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} mmm-courier.jar
ENTRYPOINT ["java","-jar", "/mmm-courier.jar"]
#deneme