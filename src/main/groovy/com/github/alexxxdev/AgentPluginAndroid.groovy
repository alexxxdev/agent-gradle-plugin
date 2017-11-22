package com.github.alexxxdev

import groovy.util.logging.Slf4j
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

@Slf4j
class AgentPluginAndroid implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.tasks.withType(Test) {
            doFirst {
                if (!project.applicationAgent.applyToTests)
                    return

                project.configurations.agent.each { agent ->
                    jvmArgs += ["-javaagent:${agent.path}"]
                }
            }
        }
    }
}