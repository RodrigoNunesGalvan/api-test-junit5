FROM openjdk:11-jre-slim-buster

ENV TZ='GMT-3'

WORKDIR /app

COPY target/api-test-junit5.jar .

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} api-test-junit5.jar

RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid 10014 \
    "choreo"
# Use the above created unprivileged user
USER 10014


ENTRYPOINT ["java","-Xmx1g","-jar","/api-test-junit5.jar"]