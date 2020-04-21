FROM openjdk:8
ADD target/sports-club.jar sports-club.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "sports-club.jar"]