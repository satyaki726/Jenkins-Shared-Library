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
                    withSonarQubeEnv('sonarqube-9.5') { 
                         bat "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.organization={ORG_KEY} -Dsonar.login={GENERATED_KEY_ON_SONARCLOUD_SECURITY} -Dsonar.branch=master"
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