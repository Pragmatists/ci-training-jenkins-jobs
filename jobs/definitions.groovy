def scmUrl = 'https://github.com/Pragmatists/testing-examples'

job('Exercise_4_solution') {
    scm {
        git(scmUrl)
    }
    steps {
        maven('clean package')
    }

    publishers {
        archiveArtifacts('target/*.jar')
    }
}

job('Exercise_5_solution') {
    scm {
        git(scmUrl)
    }

    triggers {
        scm('* * * * *')
    }
    steps {
        maven('clean package')
    }

    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit 'target/surefire-reports/TEST-*.xml'
        extendedEmail {
            recipientList('me@halfempty.org')
            defaultSubject('Oops')
            defaultContent('Something broken')
            contentType('text/html')
            triggers {
                failure()
            }
        }
    }
}

job('Exercise_6_solution') {
    scm {
        git(scmUrl)
    }

    triggers {
        scm('* * * * *')
    }
    steps {
        maven('clean package')
    }

    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit 'target/surefire-reports/TEST-*.xml'
        extendedEmail {
            recipientList('me@halfempty.org')
            defaultSubject('Oops')
            defaultContent('Something broken')
            contentType('text/html')
            triggers {
                failure()
            }
        }

        publishHtml {
            report('/target/site/jacoco/') {
                reportName('Cucumber Tests')
            }
        }
        cucumberReports {
            jsonReportPath('target/cucumber.json')

        }

    }
}

pipelineJob('Exercise_7_solution') {
    definition {
        cps {
            sandbox()
            //script(readFileFromWorkspace('ppipeline.groovy'))
            script(
                    """
                    node {
                      stage('checkout') {
                        git 'https://github.com/Pragmatists/testing-examples'
                      }
                      stage('package') {
                        sh 'mvn clean package'
                      }
                    }
                    """.stripIndent())

        }
    }
}