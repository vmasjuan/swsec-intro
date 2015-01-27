FROM maven:3.2-jdk-7-onbuild
MAINTAINER Cristián Rojas

RUN apt-get update
RUN apt-get install -y nano vim emacs24-nox

EXPOSE 8080
CMD ["mvn", "jetty:run"]