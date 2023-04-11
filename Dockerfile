FROM openjdk:17-jdk-alpine
EXPOSE 8080
COPY target/project-docker.jar /project-docker.jar
COPY auction.db /
ENTRYPOINT ["java", "-jar","/project-docker.jar"]