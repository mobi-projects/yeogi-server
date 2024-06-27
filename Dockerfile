FROM openjdk:17
CMD ["./gradlew", "clean", "build", "-x","test"]
ARG JAR_FILE_PATH=./build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-Djasypt.encryptor.password=yeogi", "-jar", "/app.jar"]
