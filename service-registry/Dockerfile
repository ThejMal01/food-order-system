FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build

COPY ../pom.xml .

COPY pom.xml service-registry/pom.xml
RUN mvn -N -f service-registry/pom.xml dependency:go-offline -B

COPY service-registry service-registry

RUN mvn -N -f service-registry/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/service-registry/target/service-registry-*.jar service-registry.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "service-registry.jar"]