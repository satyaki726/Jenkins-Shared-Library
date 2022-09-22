def call(String repoUrl){
     pipeline {
       agent any
       stages {
            stage("Checkout Code") {
               steps {
                   git branch: 'main',
                       url: "${repoUrl}"
               }
           }
           stage('hello'){
            steps{
                bat 'pwbat hello.ps1'
            }
           }
        } 
    }
}