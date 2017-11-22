package com.github.alexxxdev

import groovy.util.logging.Slf4j
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.tasks.testing.Test

@Slf4j
class AgentPluginJava implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.getPlugins().apply(ApplicationPlugin.class)

        project.startScripts {
            doLast {
                if (!project.applicationAgent.applyToStartScripts)
                    return

                project.configurations.agent.each { agent ->
                    String agentFileName = agent.name
                    String forwardSlash = "/"
                    String unixRegex = $/exec "$$JAVACMD" /$
                    String unixReplacement = $/exec "$$JAVACMD" -javaagent:"$$APP_HOME/lib${forwardSlash}${
                        agentFileName
                    }" /$
                    unixScript.text = unixScript.text.replace(unixRegex, unixReplacement)
                    String windowsRegex = $/"%JAVA_EXE%" %DEFAULT_JVM_OPTS%/$
                    String windowsReplacement = $/"%JAVA_EXE%" %DEFAULT_JVM_OPTS% -javaagent:"%APP_HOME%\lib\$agentFileName"/$
                    windowsScript.text = windowsScript.text.replace(windowsRegex, windowsReplacement)
                }
            }
        }

        project.tasks.withType(Test) {
            doFirst {
                if (!project.applicationAgent.applyToTests)
                    return

                project.configurations.agent.each { agent ->
                    jvmArgs += ["-javaagent:${agent.path}"]
                }
            }
        }

        project.tasks.run {
            doFirst {
                if (!project.applicationAgent.applyToRun)
                    return

                project.configurations.agent.each { agent ->
                    project.applicationDefaultJvmArgs += ["-javaagent:${agent.path}"]
                }
            }
        }
    }
}