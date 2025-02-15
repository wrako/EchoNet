FROM amazoncorretto:21
WORKDIR /app
COPY target/*.jar pastebin-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pastebin-service.jar"]


