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
           stage('zip'){
                steps{
                    powershell 'Compress-Archive -Path C:/Users/hp/.jenkins/workspace/JenikinsPipe1/target -DestinationPath C:/Users/hp/.jenkins/workspace/target.zip'
                }
           }
           stage('Test'){
                steps{
                    bat ".\mvnw test"
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