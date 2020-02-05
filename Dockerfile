FROM maven:3.6.3-ibmjava-alpine

RUN ["mkdir", "/app"]
WORKDIR /app
COPY pom.xml /app/pom.xml
COPY src/ /app/src
RUN ["mvn", "compile", "integration-test"]

EXPOSE 8080
ENTRYPOINT ["mvn", "jetty:run"]
