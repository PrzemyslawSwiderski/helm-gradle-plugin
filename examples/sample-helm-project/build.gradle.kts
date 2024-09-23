import com.pswidersk.gradle.helm.HelmTask

plugins {
    id("com.pswidersk.helm-plugin") version "1.0.6"
}

helmPlugin {
    helmVersion = "3.2.3"
}

tasks {

    register<HelmTask>("helm") {
        // exec args can be passed by commandline, for example
        // ./gradlew helm --args="--help"
    }

    register<HelmTask>("helmHelp") {
        args("--help")
    }

    register<HelmTask>("helmVersion") {
        args("version", "--short")
    }

    register<HelmTask>("helmCreate") {
        args("create", "my-chart")
    }

    register<HelmTask>("helmTest") {
        args("test", "my-chart", "--namespace", "my-chart")
    }

    register<HelmTask>("helmUpgradeOrInstallToMinikube") {
        // More available at: https://helm.sh/docs/helm/helm_upgrade/
        args(
            "upgrade", "--install", "my-chart", "./my-chart",
            "--namespace", "my-chart", "--create-namespace", "--kube-context", "minikube", "--atomic"
        )
    }

    register<HelmTask>("helmUpgradeOrInstallToDockerDesktop") {
        args(
            "upgrade", "--install", "my-chart", "./my-chart",
            "--namespace", "my-chart", "--create-namespace", "--kube-context", "docker-desktop", "--atomic"
        )
    }

    register<HelmTask>("helmStatus") {
        args("status", "my-chart", "--namespace", "my-chart")
    }

    register<HelmTask>("helmLint") {
        args("lint", "my-chart", "--namespace", "my-chart")
    }

    register<HelmTask>("helmTemplate") {
        args("template", "my-chart", "--namespace", "my-chart")
    }

    register<HelmTask>("helmUninstall") {
        args("uninstall", "my-chart", "--namespace", "my-chart")
    }

}
