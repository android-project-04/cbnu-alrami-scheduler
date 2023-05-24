FROM openjdk:17-jdk
COPY build/libs/cbnu-alrami-scheduler-5.5.5.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
