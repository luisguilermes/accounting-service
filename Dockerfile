FROM adoptopenjdk/openjdk11:alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} accounting-service-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/accounting-service-1.0-SNAPSHOT.jar"]