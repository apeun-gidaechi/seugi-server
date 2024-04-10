FROM openjdk:21-jdk
COPY build/libs/*.jar /app/
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
