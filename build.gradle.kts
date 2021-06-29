plugins {
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.serialization") version "1.5.20"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    application
}

application {
    mainClassName = "net.michal.basic.BootstrapKt"
    mainClass.set("net.michal.basic.BootstrapKt")
}

group = "net.michal"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-client-cio:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.1")
}
