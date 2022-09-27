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
           stage("Build workspace") {
               steps {
                   bat "mvn install"
               }
           }
           stage('SonarQube analysis') {
                steps{
                    withSonarQubeEnv('sonarqube-9.5') { 
                         bat "mvn sonar:sonar"
                    }
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