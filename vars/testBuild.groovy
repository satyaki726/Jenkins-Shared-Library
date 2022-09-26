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
                }
           }
       }
   }
}