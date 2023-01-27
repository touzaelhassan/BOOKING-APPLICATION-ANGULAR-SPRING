pipeline {

    agent any
    triggers {
       pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                dir("BACKEND-APPLICATION/"){
                    bat 'mvn install -DskipTests'
                }
            }
        }
        stage('Deploy') {
            steps {
                bat 'docker-compose build'
                bat 'docker-compose up -d'
            }
        }
        stage('Checkout') {
            steps {
               checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/touzaelhassan/HOTEL-BOOKING-APPLICATION.git']]])
            }
        }
    }
}