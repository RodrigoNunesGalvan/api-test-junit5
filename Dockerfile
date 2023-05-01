#FROM openjdk:11-jre-slim-buster
#
#ENV TZ='GMT-3'
#
#WORKDIR /app
#
#COPY target/api-test-junit5.jar .
#
#VOLUME /tmp
#
#EXPOSE 8080
#
#ARG JAR_FILE=target/*.jar
#
#ADD ${JAR_FILE} api-test-junit5.jar
#
#RUN adduser \
#    --disabled-password \
#    --gecos "" \
#    --home "/nonexistent" \
#    --shell "/sbin/nologin" \
#    --no-create-home \
#    --uid 10014 \
#    "choreo"
## Use the above created unprivileged user
#USER 10014
#
#
#ENTRYPOINT ["java","-Xmx1g","-jar","/api-test-junit5.jar"]

FROM openjdk:11-jdk-slim as build-env

# Instala o Maven
RUN apt-get update && \
    apt-get install -y maven

# Cria um diretório para a aplicação
RUN mkdir /app

# Define o diretório de trabalho como /app
WORKDIR /app

# Copia o arquivo pom.xml
COPY pom.xml .

# Baixa as dependências do Maven - isso será cacheado se o pom.xml não mudar
RUN mvn dependency:go-offline

# Copia o código-fonte da aplicação
COPY src ./src

# Compila a aplicação
RUN mvn package

# Cria uma imagem menor para a execução
FROM openjdk:11-jre-slim

# Cria um usuário para rodar a aplicação
RUN addgroup --system --gid 10014 choreo && \
    adduser --system --uid 10014 --ingroup choreo choreouser

# Copia o arquivo JAR gerado para dentro da imagem
COPY --from=build-env /app/target/api-test-junit5.jar /app/api-test-junit5.jar

# Define o usuário que irá executar a aplicação
USER choreouser

# Define o comando para iniciar a aplicação
CMD ["java", "-jar", "/app/api-test-junit5.jar"]