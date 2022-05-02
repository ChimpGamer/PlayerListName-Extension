plugins {
    kotlin("jvm") version "1.6.21"
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "nl.chimpgamer.networkmanager.extensions"
version = "1.0.6"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://repo.networkmanager.xyz/repository/maven-public")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("nl.chimpgamer.networkmanager:api:2.10.0")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.github.Carleslc:Simple-YAML:1.7.2")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    processResources {
        expand("version" to project.version)
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