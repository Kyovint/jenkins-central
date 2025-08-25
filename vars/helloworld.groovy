// vars/standardPipeline.groovy

def call(Map config = [:]) {
    // Configuración por defecto
    def agentLabel = config.agentLabel ?: 'jenkins-agent'
    def branchName = env.BRANCH_NAME ?: 'unknown'

    pipeline {
        agent {
            kubernetes {
                label agentLabel
            }
        }

        stages {
            stage('📥 Checkout') {
                steps {
                    script {
                        echo "🔹 Iniciando pipeline para la rama: ${branchName}"
                        echo "🔹 Commit actual: ${env.GIT_COMMIT}"
                    }
                    checkout scm
                }
            }

            stage('🔍 Mostrar cambios') {
                steps {
                    script {
                        def changes = sh(
                            script: 'git log --oneline -n 5',
                            returnStdout: true
                        ).trim()
                        echo "📌 Últimos cambios:\n${changes}"
                    }
                }
            }

            stage('👋 Hello World') {
                steps {
                    sh """
                    echo "🎉 ¡Hola! Este pipeline se disparó porque hubo un cambio en la rama '${branchName}'."
                    echo "📦 Este repositorio tiene \$(ls -1 | wc -l) archivos en la raíz."
                    """
                }
            }
        }

        post {
            success {
                echo "✅ Pipeline completado con éxito."
            }
            failure {
                echo "❌ El pipeline falló. Revisa los logs."
            }
        }
    }
}