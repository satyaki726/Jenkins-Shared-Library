def call(String repoUrl) {
  pipeline {
       agent any
       tools {
           maven "maven-3.8.6"
       }
       stages {
           stage("Tools initialization") {
               steps {
                   bat "mvn --version"
                   bat "java -version"
               }
           }
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
                         bat "mvn clean package sonar:sonar"
                    }
                }
            } 
       }
   }
}