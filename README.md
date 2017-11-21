# agent-gradle-plugin
[![Release](https://jitpack.io/v/alexxxdev/agent-gradle-plugin.svg)](https://jitpack.io/#alexxxdev/agent-gradle-plugin)

A Gradle plugin for hooking up a Java Agent for Java And Android projects.
This plugin will attach the agent you specify to the `test` tasks.

### Setup
```Groovy
buildscript {
  repositories {	
	  maven { url 'https://jitpack.io' }
	}
  dependencies{
    classpath "com.github.alexxxdev:agent-gradle-plugin:1.0.0"
  }
}

apply plugin: 'com.github.alexxxdev.application-agent'

dependencies { 
  agent "com.foo:bar:1.2.3"
}
