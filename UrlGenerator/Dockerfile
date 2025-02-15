FROM amazoncorretto:21

WORKDIR /app

COPY target/*.jar url-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "url-service.jar"]


