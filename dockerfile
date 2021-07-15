FROM 2281234/gradle7.1.1-openjdk14 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:14-jdk-alpine
EXPOSE 8080
RUN mkdir /app

COPY --from=build /home/gradle/src/build/resources/main/config/* /app/src/main/resources/config/
COPY --from=build /home/gradle/src/build/resources/main/keystore.jceks /app/src/main/resources/
COPY --from=build /home/gradle/src/build/resources/main/log4j.properties /app/src/main/resources/
COPY --from=build /home/gradle/src/build/libs/oil_microservice-1.0-all.jar /app/
WORKDIR /app
ENTRYPOINT ["java","-DENV=production", "-DdataSource=postgres", "-DsecretBuffer=secret", "-DkeyStorePath=src/main/resources/keystore.jceks", "-DkeyStorePassword=12345678", "-Dlog4j.error", "-jar", "oil_microservice-1.0-all.jar"]
