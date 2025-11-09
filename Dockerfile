FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 1555
ENTRYPOINT ["java", "-jar", "/app/app.jar"]