#Build Stage
FROM docker.io/maven:3.9.2-eclipse-temurin-17-alpine as build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

#Run Stage
FROM docker.io/eclipse-temurin:17-jdk-alpine
COPY --from=build /usr/src/app/target/modexp-0.1.war /usr/app/app.war
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/app/app.war"]
