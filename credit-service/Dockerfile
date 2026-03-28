FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/credit-service-1.0.0.jar app.jar
EXPOSE 7004
ENTRYPOINT ["java","-jar","/app/app.jar"]
