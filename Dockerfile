FROM eclipse-temurin:11-jdk
EXPOSE 8080
VOLUME /tmp
COPY src src
COPY pom.xml pom.xml
COPY .mvn .mvn
COPY mvnw mvnw
RUN ["./mvnw","package"]
ENTRYPOINT ["./mvnw","spring-boot:run"]

