 # FROM openjdk:20-ea-17-jdk
FROM gradle:7.4.0-jdk17
WORKDIR /app
COPY --chown=gradle:gradle . .
#RUN gradle build --refresh-dependencies
EXPOSE 8080
CMD ["./gradlew", "bootrun"]
#CMD ["sh"]