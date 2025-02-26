#Build Stage
FROM docker.io/maven:3.9.2-eclipse-temurin-17-alpine as build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

#Run Stage
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /usr/src/app/target/modexp-0.1.jar /usr/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
