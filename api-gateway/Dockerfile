FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build

COPY . .

RUN mvn clean package -pl api-gateway -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/api-gateway/target/api-gateway-*.jar api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]