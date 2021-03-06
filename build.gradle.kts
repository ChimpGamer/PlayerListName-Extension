import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    base
    kotlin("jvm") version "1.4.10"
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "nl.chimpgamer.networkmanager.extensions"
version = "1.0.5"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly(files("./libs/NetworkManagerAPI-v2.9.0.jar"))
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.github.Carleslc:Simple-YAML:1.4.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    processResources {
        val tokens = mapOf("version" to project.version)
        from(sourceSets["main"].resources.srcDirs) {
            filter<ReplaceTokens>("tokens" to tokens)
        }
    }
    shadowJar {
        archiveFileName.set("${project.name}-v${project.version}.jar")
        relocate("kotlin", "nl.chimpgamer.networkmanager.lib.kotlin")
        relocate("org.simpleyaml", "nl.chimpgamer.networkmanager.lib.simpleyaml")
    }
    build {
        dependsOn(shadowJar)
    }
    jar {
        enabled = false
    }
}