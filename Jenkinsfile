currentBuild.displayName = "springboot-#"+currentBuild.number

pipeline{ 
  stages{
    stage("code check"){
        steps{
            withSonarQubeEnv('sonar') {
                sh " ./gradlew clean build sonar:sonar"
            }
        }
    }
  }
}
