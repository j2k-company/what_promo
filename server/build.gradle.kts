plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "com.j2k"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val ktorVersion = "2.1.0"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("MainKt")
}
