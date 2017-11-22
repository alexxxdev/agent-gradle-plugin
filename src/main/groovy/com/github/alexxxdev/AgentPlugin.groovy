package com.github.alexxxdev

import com.android.build.gradle.BasePlugin
import groovy.util.logging.Slf4j
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaPlugin

@Slf4j
class AgentPlugin implements Plugin<Project> {

    void apply(Project project) {

        project.extensions.create("applicationAgent", AgentPluginExtension)

        project.configurations {
            agent
            runtime.extendsFrom(agent)
        }

        def appPlugin = [ApplicationPlugin]
                .collect { project.plugins.findPlugin(it) as ApplicationPlugin }
                .find { it != null }

        def javaPlugin = [JavaPlugin]
                .collect { project.plugins.findPlugin(it) as JavaPlugin }
                .find { it != null }

        def androidPlugin = ['android', 'com.android.application', 'android-library', 'com.android.library',
                             'com.android.test', 'com.android.feature']
                .collect { project.plugins.findPlugin(it) as BasePlugin }
                .find { it != null }

        if (appPlugin != null) {
            project.apply plugin: AgentPluginJava
        } else if (javaPlugin != null) {
            project.apply plugin: AgentPluginJava
        } else if (androidPlugin != null) {
            project.apply plugin: AgentPluginAndroid
        } else {
            log.warn("Not found suitable plugin")
        }
    }
}

class AgentPluginExtension {
    Boolean applyToTests = true
    Boolean applyToRun = true
    Boolean applyToStartScripts = true
}