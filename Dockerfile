FROM openjdk:11

WORKDIR /app

ENV TZ='GMT-3'

COPY . .

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