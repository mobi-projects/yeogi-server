FROM openjdk:17
CMD ["./gradlew", "clean", "build", "-x","test"]
ARG JAR_FILE_PATH=./build/libs/*.jar
ARG JASYPT_PASSWORD

COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-Djasypt.encryptor.password=${JASYPT_PASSWORD}", "-jar", "/app.jar"]
