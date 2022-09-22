def call(String repoUrl){
     pipeline {
       agent any
       stages {
            stage("Checkout Code") {
               steps {
                   git branch: 'master',
                       url: "${repoUrl}"
               }
           }
           stage('version'){
            steps{
                bat 'pwsh --version'
            }
           }
           stage('hello'){
            steps{
                bat 'pwsh hello.ps1'
            }
           }
        } 
    }
}