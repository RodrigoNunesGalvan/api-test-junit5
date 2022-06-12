pipeline {
    agent any
    tools {
        maven 'Maven 3.8.1'
        jdk 'jdk8'
    }
    stages {
        stage ('Build') {
            steps {
                sh ' mvn clean install -DskipTests'
            }
        }
        stage ('Test') {
            steps {
                sh ' mvn test'
            }
        }
        stage ('Imagem docker') {
            steps {
                sh 'docker build . -t a2like/api-test-junit5:${BUILD_NUMBER}'
            }
        }
        stage ('Run docker') {
            steps {
                sh ' docker stop api-test-junit5 || true'
                sh ' docker rm api-test-junit5 || true'
                sh ' docker container run --network intranet -h api-test-junit5 -d --name api-test-junit5 -p 8080:8080 a2like/api-test-junit5:${BUILD_NUMBER}'
            }
        }
    }
}