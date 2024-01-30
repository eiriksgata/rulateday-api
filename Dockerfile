FROM adoptopenjdk/openjdk11:ubi
MAINTAINER Eiriksgata <rulateday@gmail.com>

VOLUME /tmp/rulateday-api-server-java/

# Add the renamed service itself
ADD /target/rulateday-api-server-SNAPSHOT.jar app.jar

# Expose Port
EXPOSE 16467

ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
