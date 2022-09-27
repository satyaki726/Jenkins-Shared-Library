def call(String repoUrl) {
  pipeline {
       agent any
       tools {
           maven "maven-3.8.6"
       }
       stages {
           stage("Checkout Code") {
               steps {
                   git branch: 'master',
                       url: "${repoUrl}"
               }
           }
          stage('SonarQube Analysis') {
             def mvn = tool 'maven-3.8.6';
             withSonarQubeEnv() {
                 bat "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=junit-app"
            }
          }
           stage('Test'){
                steps{
                    bat "mvn test"
                }
                post{
                    always{
                        junit '**/target/surefire-reports/TEST-*.xml'
                    }
                }
           }
       }
    }   
}