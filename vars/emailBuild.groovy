def call(String repoUrl,String body,String subject,String tomail) {
  pipeline {
       agent any
       stages {
           stage("Checkout Code") {
               steps {
                   git branch: 'master',
                       url: "${repoUrl}"
               }
           }
           
        post{
            success{
            emailext body: "${body}", 
            subject: "${subject}",
            to: "${tomail}"
            }
        }
       }
   }
}