FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build

COPY . .

RUN mvn clean package -pl auth -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /build/auth/target/auth-*.jar auth.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "auth.jar"]