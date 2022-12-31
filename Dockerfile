FROM openjdk:11
MAINTAINER yashar
COPY target/ASM-api-0.0.1-SNAPSHOT.jar ASM-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ASM-api-0.0.1-SNAPSHOT.jar"]