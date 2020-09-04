# Gradle Helm plugin
## About
Simple Gradle plugin to wrap [Helm](https://helm.sh/) executable as tasks. 

By using this plugin there is no need to download and install helm client.
Helm executable is downloaded and unpacked in `.gradle` dir.
All major operating systems such as Linux, Windows, Mac OS are supported.

## Requirements
* JRE 8 or higher to run gradle wrapper
* configured Kubernetes context to be used by helm client


## Usage
### Steps to run helm commands by using Gradle
1. Apply a plugin to a project as described on [gradle portal](https://plugins.gradle.org/plugin/com.pswidersk.helm-plugin).
2. Configure a plugin by specifying desired helm version in build script (default: `3.3.1`):
    ```kotlin
    helmPlugin {
        helmVersion.set("3.2.2")
    }
    ```
3. Define a task to run desired helm client command, for example:
    ```kotlin
    tasks {
        register<HelmTask>("helmHelp") {
            args("--help")
        }
    }
    ```
4. Run helm command from gradle:
    ```shell script
    # Linux
    ./gradlew helmHelp
    # Windows
    gradlew.bat helmHelp
    ```

### Additional examples can be found in `examples` module in this project. 

## [Changelog](./CHANGELOG.md)