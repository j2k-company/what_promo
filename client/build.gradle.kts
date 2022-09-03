
var outPath = "$projectDir/../server/src/main/resources/web/"

plugins {
    id("org.jetbrains.kotlin.js") version "1.6.10"
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
}

kotlin {
    js {
        browser {
            webpackTask {
                this.destinationDirectory = File(outPath)
                cssSupport.enabled = true

                dependsOn(tasks.named("copyIndexHTML").get())
            }

            runTask {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
        binaries.executable()
    }
}

tasks.register<Copy>("copyIndexHTML") {
    from(file("src/main/resources/index.html"))
    into(outPath)
}

rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
    versions.webpackCli.version = "4.10.0"
}
