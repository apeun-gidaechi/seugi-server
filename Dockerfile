FROM openjdk:21-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app/
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
