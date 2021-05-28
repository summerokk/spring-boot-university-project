FROM adoptopenjdk/openjdk8
ARG JAR_FILE=*.jar
COPY ./target/university.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
