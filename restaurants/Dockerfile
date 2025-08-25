FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build

COPY . .

RUN mvn clean package -pl restaurants -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/restaurants/target/restaurants-*.jar restaurants.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "restaurants.jar"]