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
  }
}
