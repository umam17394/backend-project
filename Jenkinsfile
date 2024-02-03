pipeline {
    agent any

    stages {
        stage('docker build') {
            steps {
                sh 'docker build -t javaspring:v1 .'
            }
        }
      stage('docker tag') {
            steps {
                sh 'docker tag javaspring:v1 prasadchandu/java:spring-v1'
            }
        }
      stage('docker login') {
            steps {
                sh 'docker login'
            }
        }
      stage('docker push') {
            steps {
                sh 'docker push prasadchandu/java:spring-v1'
            }
      }
        stage('sonar') {
            steps {
                mvn clean verify sonar:sonar \
                  -Dsonar.projectKey=project-M1 \
                  -Dsonar.host.url=http://18.61.18.8:9000 \
                  -Dsonar.login=sqp_83ef7e50abc0ed05d3eed2213825117ef5e8c114
            }
        }
    }
}
