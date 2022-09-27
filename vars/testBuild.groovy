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
            stage('SonarQube analysis') {
                steps{
                    withSonarQubeEnv('sonarqube-9.5',envOnly: true) { 
                        ${env.SONAR_AUTH_TOKEN} = "sqp_f90ab3e9c0c4b532043a3db0f850c07d19b431eb"
                         bat "mvn clean package sonar:sonar"
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