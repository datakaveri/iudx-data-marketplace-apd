pipeline {


  environment {
    devRegistry = 'ghcr.io/datakaveri/dmp-apd-server-dev'
    deplRegistry = 'ghcr.io/datakaveri/dmp-apd-server-depl'
    registryUri = 'https://ghcr.io'
    registryCredential = 'datakaveri-ghcr'
    GIT_HASH = GIT_COMMIT.take(7)
  }

  agent {
    node {
      label 'slave1'
    }
  }
  stages {

    stage('Build images') {
      steps{
        script {
          devImage = docker.build( devRegistry, "-f ./docker/dev.dockerfile .")
          deplImage = docker.build( deplRegistry, "-f ./docker/depl.dockerfile .")
        }
      }
    }

    stage('Unit Tests and Code Coverage Test'){
      steps{
        script{
            sh "mvn clean test checkstyle:checkstyle pmd:pmd"
        }
        xunit (
          thresholds: [ skipped(failureThreshold: '0'), failed(failureThreshold: '0') ],
          tools: [ JUnit(pattern: 'target/surefire-reports/*.xml') ]
        )
        jacoco classPattern: 'target/classes', execPattern: 'target/jacoco.exec', sourcePattern: 'src/main/java', exclusionPattern:'iudx/data/marketplace/apiserver/ApiServerVerticle.class,**/*VertxEBProxy.class,**/Constants.class,**/*VertxProxyHandler.class,**/*Verticle.clas,**/JwtDataConverter.class,iudx/data/marketplace/apiserver/ProviderApis.class,iudx/data/marketplace/apiserver/ConsumerApis.class,iudx/data/marketplace/deploy/*.class'
      }
      post{
      always {
        recordIssues(
          enabledForFailure: true,
          blameDisabled: true,
          forensicsDisabled: true,
          qualityGates: [[threshold:0, type: 'TOTAL', unstable: false]],
          tool: checkStyle(pattern: 'target/checkstyle-result.xml')
        )
        recordIssues(
          enabledForFailure: true,
          blameDisabled: true,
          forensicsDisabled: true,
          qualityGates: [[threshold:0, type: 'TOTAL', unstable: false]],
          tool: pmdParser(pattern: 'target/pmd.xml')
        )
      }
        failure{
          error "Test failure. Stopping pipeline execution!"
        }
        cleanup{
          script{
            sh 'sudo rm -rf target/'
          }
        }        
      }
    }
    stage('Start DMP-APD-SERVER for Performance/Integration Testing'){
      steps{
        script{
          sh 'scp src/test/resources/IUDX-Data-Marketplace-APIs.postman_collection.json jenkins@jenkins-master:/var/lib/jenkins/iudx/dmp-apd/Newman/'
          sh 'docker compose -f docker-compose.test.yml up -d integTest'
          sh 'sleep 45'
        }
      }
      post{
        failure{
          script{
            sh 'docker compose -f docker-compose.test.yml down --remove-orphans'
          }
        }
      }
    }

    stage('Integration tests & OWASP ZAP pen test'){
      steps{
        node('built-in') {
          script{
            startZap ([host: 'localhost', port: 8090, zapHome: '/var/lib/jenkins/tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/OWASP_ZAP/ZAP_2.11.0'])
              sh 'curl http://127.0.0.1:8090/JSON/pscan/action/disableScanners/?ids=10096'
              sh 'HTTP_PROXY=\'127.0.0.1:8090\' newman run /var/lib/jenkins/iudx/dmp-apd/Newman/IUDX-Data-Marketplace-APIs.postman_collection.json -e /home/ubuntu/configs/dmp-apd-postman-env.json -n 2 --insecure -r htmlextra --reporter-htmlextra-export /var/lib/jenkins/iudx/dmp-apd/Newman/report/report.html --reporter-htmlextra-skipSensitiveData'
            runZapAttack()
          }
        }
      }
      post{
        always{
          node('built-in') {
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: '/var/lib/jenkins/iudx/dmp-apd/Newman/report/', reportFiles: 'report.html', reportTitles: '', reportName: 'Integration Test Report'])
            script{
              archiveZap failHighAlerts: 1, failMediumAlerts: 1, failLowAlerts: 1
            }  
          }
        }
        failure{
          error "Test failure. Stopping pipeline execution!"
        }
        cleanup{
          script{
            sh 'docker compose -f docker-compose.test.yml down --remove-orphans'
          }
        }
      }
    }

    stage('Continuous Deployment') {
      when {
        allOf {
          anyOf {
            changeset "docker/**"
            changeset "docs/**"
            changeset "pom.xml"
            changeset "src/main/**"
            triggeredBy cause: 'UserIdCause'
          }
          expression {
            return env.GIT_BRANCH == 'origin/main';
          }
        }
      }
      stages {
        stage('Push Images') {
          steps {
            script {
              docker.withRegistry( registryUri, registryCredential ) {
                devImage.push("1.0.0-alpha-${env.GIT_HASH}")
                deplImage.push("1.0.0-alpha-${env.GIT_HASH}")
              }
            }
          }
        }
        stage('Docker Swarm deployment') {
          steps {
            script {
              sh "ssh azureuser@docker-swarm 'docker service update dmp_apd_dmp-apd --image ghcr.io/datakaveri/dmp-apd-server-depl:1.0.0-alpha-${env.GIT_HASH}'"
              sh 'sleep 10'
            }
          }
          post{
            failure{
              error "Failed to deploy image in Docker Swarm"
            }
          }          
        }

        stage('Integration test on Swarm deployment') {
          steps {
            node('built-in') {
              script{
                sh 'newman run /var/lib/jenkins/iudx/dmp-apd/Newman/IUDX-Data-Marketplace-APIs.postman_collection.json -e /home/ubuntu/configs/cd/dmp-apd-postman-env.json --insecure -r htmlextra --reporter-htmlextra-export /var/lib/jenkins/iudx/dmp-apd/Newman/report/cd-report.html --reporter-htmlextra-skipSensitiveData'
              }
            }
          }
          post{
            always{
              node('built-in') {
                script{
                  publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: '/var/lib/jenkins/iudx/dmp-apd/Newman/report/', reportFiles: 'cd-report.html', reportTitles: '', reportName: 'Swarm Integration Test Report'])
                }
              }
            }
            failure{
              error "Test failure. Stopping pipeline execution!"
            }
          }

        }
      }
    }
  }

    post{
        failure{
        script{
            if (env.GIT_BRANCH == 'origin/main')
            emailext recipientProviders: [buildUser(), developers()], to: '$DMP_APD_RECIPENTS, $DEFAULT_RECIPIENTS', subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!', body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:
            Check console output at $BUILD_URL to view the results.'''
        }
    }
 }
}