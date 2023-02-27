FROM eclipse-temurin:19

#Update system
RUN apt-get update && apt-get upgrade -y

#Install nano
RUN apt-get install -y nano

#Doxer
WORKDIR /
RUN mkdir app

COPY ./target /app
COPY ./starter /app

WORKDIR /app
RUN mv DoxerBot-1.0-jar-with-dependencies.jar doxerbot.jar
#RUN ./start.sh