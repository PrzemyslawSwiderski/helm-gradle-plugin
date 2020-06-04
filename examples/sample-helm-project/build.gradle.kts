import com.pswidersk.gradle.helm.HelmTask

plugins {
    id("com.pswidersk.helm-plugin") version "1.0.0"
}

helmPlugin {
    helmVersion.set("3.2.2")
}

tasks {
    register<HelmTask>("helmHelp") {
        args("--help")
    }

    register<HelmTask>("helmVersion") {
        args("version")
    }
}