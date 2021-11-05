FROM openjdk:11
LABEL maintainer="nicumax92@gmail.com"
VOLUME /main-app
ADD target/sweet_companion_bot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 5000
ENTRYPOINT ["java", "-jar","/app.jar"]