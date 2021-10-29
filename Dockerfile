FROM maven:3.6.3 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app

RUN mvn clean package dependency:resolve -DskipTests

FROM openjdk:11

ARG JAR_FILE=erasmus-system-backend-application.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

#COPY target/erasmus-system-backend-application.jar erasmus-system-backend-application.jar

ENTRYPOINT ["java","-jar","erasmus-system-backend-application.jar"]