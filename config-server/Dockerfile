FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build

COPY ../pom.xml .

COPY pom.xml config-server/pom.xml
RUN mvn -N -f config-server/pom.xml dependency:go-offline -B

COPY config-server config-server

RUN mvn -N -f config-server/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/config-server/target/config-server-*.jar config-server.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "config-server.jar"]