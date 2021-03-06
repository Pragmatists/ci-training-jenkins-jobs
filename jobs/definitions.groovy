def scmUrl = 'https://github.com/Pragmatists/testing-examples'

job('Exercise_4_solution') {
    scm {
        git {
            remote {
                url(scmUrl)
            }
            branch('master')
        }
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
        git {
            remote {
                url(scmUrl)
            }
            branch('master')
        }
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
        mailer('me@example.com', true, true)
    }
}

job('Exercise_6_solution') {
    scm {
        git {
            remote {
                url(scmUrl)
            }
            branch('master')
        }
    }

    triggers {
        scm('* * * * *')
    }
    steps {
        maven('clean verify')
    }

    publishers {
        archiveArtifacts('target/*.jar')
        archiveJunit 'target/surefire-reports/TEST-*.xml'
        mailer('me@example.com', true, true)

        publishHtml {
            report('target/site/jacoco/') {
                reportName('Code coverage')
            }
        }
        cucumberReports {
            jsonReportPath('target/')
            fileIncludePattern('**/cucumber.json')

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