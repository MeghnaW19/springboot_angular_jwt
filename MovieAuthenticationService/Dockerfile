FROM java:8-jre
WORKDIR usr/src
ENV MYSQL_DATABASE=movieDb
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_CI_USER=jdbc:mysql://localhost:3306/movieDb
ADD ./target/MovieAuthenticationService-0.0.1-SNAPSHOT.jar /usr/src/MovieAuthenticationService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "MovieAuthenticationService-0.0.1-SNAPSHOT.jar"]
