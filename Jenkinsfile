currentBuild.displayName = "springboot-#"+currentBuild.number

pipeline{ 
  agent any  
  stages{
    stage("code check"){
        steps{
            withSonarQubeEnv('sonar') {
                sh " ./gradlew sonarqube"
            }
        }
    }
  stage('Sonar scan result check') {
        steps {
            timeout(time: 5, unit: 'MINUTES') {
            retry(3) {
            script {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
                if (qg.status == 'OK') {
                    echo "Pipeline quality gate Pass!!: ${qg.status}"
                }
                    }
                 } 
                }
            }
       }  
       stage("gradle build"){
        steps {
            sh "gradlew clean build"
            }
        }
  }
}
