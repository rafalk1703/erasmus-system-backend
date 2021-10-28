FROM openjdk:11

COPY target/erasmus-system-backend-application.jar erasmus-system-backend-application.jar

ENTRYPOINT ["java","-jar","/erasmus-system-backend-application.jar"]