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
           stage('Test'){
                steps{
                    bat "mvn test"
                    withSonarQubeEnv('sonarqube-9.5') { 
                         bat "mvn sonar:sonar"
                    }
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