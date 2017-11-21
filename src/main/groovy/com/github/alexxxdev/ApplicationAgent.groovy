package com.github.alexxxdev

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.tasks.testing.Test

class ApplicationAgentPlugin implements Plugin<Project> {
    void apply(Project project) {

        project.extensions.create("applicationAgent", ApplicationAgentPluginExtension)

        project.configurations {
            agent
            runtime.extendsFrom(agent)
        }

        project.tasks.withType(Test) {
            doFirst {
                if (!project.applicationAgent.applyToTests)
                    return

                project.configurations.agent.each { agent ->
                    jvmArgs += [ "-javaagent:${agent.path}" ]
                }
            }
        }
    }
}

class ApplicationAgentPluginExtension {
    Boolean applyToTests = true
}