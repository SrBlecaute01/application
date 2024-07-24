FROM gradle:jdk21-alpine AS build
WORKDIR /home
COPY . .
RUN gradle shadowJar

FROM openjdk:21-jdk-slim
WORKDIR /home

COPY server/spigot-1.21.jar .
RUN echo eula=true > eula.txt
COPY --from=build /home/home/plugin/build/libs/home*.jar ./plugins/home.jar

ENV SERVER_PORT=25565

ENTRYPOINT java -Dfile.encoding=UTF-8 -jar spigot-1.21.jar --port=${SERVER_PORT} nogui

EXPOSE ${SERVER_PORT}