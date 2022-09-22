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
                powershell 'Compress-Archive -Path H:\Course\Certificate -DestinationPath H:\Course\Something.zip'
            }
           }
        } 
    }
}