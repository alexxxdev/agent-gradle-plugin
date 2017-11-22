# agent-gradle-plugin
[![Release](https://jitpack.io/v/alexxxdev/agent-gradle-plugin.svg)](https://jitpack.io/#alexxxdev/agent-gradle-plugin)

A Gradle plugin for hooking up a Java Agent for Java And Android projects.
This plugin will attach the agent you specify to the `run`, `test`, `installDist` tasks for Java projects and `test` tasks for Android projects.

### Setup
```Groovy
buildscript {
  repositories {	
	  maven { url 'https://jitpack.io' }
	}
  dependencies{
    classpath "com.github.alexxxdev:agent-gradle-plugin:1.1.0"
  }
}

apply plugin: 'com.github.alexxxdev.agent'

// all of these are optional and default to true
applicationAgent {
	applyToTests true
	applyToRun true
	applyToStartScripts true
}

dependencies { 
  agent "com.foo:bar:1.2.3"
}
