FROM openjdk:19
ARG WAR_FILE=target/*.war
COPY target/hotel-booking-application.war app.war
ENTRYPOINT ["java", "-jar","app.war"]
