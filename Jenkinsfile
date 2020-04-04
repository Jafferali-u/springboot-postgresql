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
            sh "./gradlew clean build"
            }
        }
      stage("upload to Nexus"){
        steps{
            withCredentials([usernameColonPassword(credentialsId: 'Sonaradmin', variable: 'Sonaradmin')]) {
                sh '''
                    set +x
                    mv build/libs/spring_psql-0.0.1-SNAPSHOT.jar build/libs/spring_psql-0.0.${currentBuild.number}-SNAPSHOT.jar
                    curl -v -u "$Sonaradmin" --upload-file build/libs/spring_psql-0.0.${currentBuild.number}-SNAPSHOT.jar http://172.31.18.116:8081/repository/myrepo/
                    '''
                }
            }
        }
        stage("bulid image"){
            steps{
                sshagent(['ansadmin_ansible']) {
                  sh """
                    ssh -o StrictHostKeyChecking=no ansadmin@172.31.63.160 ansible-playbook springboot-mysql_docker-push.yaml -e ansible_python_interpreter=/usr/bin/python2.7 -e version=${currentBuild.number}
                     """
                }
            }
        }
    stage("Depoly kubernetes"){
            steps{
                sshagent(['ansadmin_ansible']) {
                  sh """
                    ssh -o StrictHostKeyChecking=no ansadmin@172.31.63.160 ansible-playbook springboot-mysql_kubernetes.yaml -e version=${currentBuild.number}
                     """
                }
            }
        }
  }
}
