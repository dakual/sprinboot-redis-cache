FROM openjdk:18.0.1-jdk-oracle

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]