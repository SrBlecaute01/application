# compile plugins
FROM gradle:jdk21-alpine AS build
WORKDIR /home
COPY . .
RUN gradle shadowJar

# use jdk and set workdir
FROM openjdk:21-jdk-slim
WORKDIR /home

# copy spigot and plugins
COPY server/spigot-1.21.jar .
COPY --from=build /home/home/plugin/build/libs/home*.jar ./plugins/home.jar
COPY --from=build /home/windcharge/build/libs/windcharge*.jar ./plugins/windcharge.jar

# enable eula
RUN echo eula=true > eula.txt

# execute server and expose port
ENV SERVER_PORT=25565
ENTRYPOINT java -Dfile.encoding=UTF-8 -jar spigot-1.21.jar --port=${SERVER_PORT} nogui
EXPOSE ${SERVER_PORT}