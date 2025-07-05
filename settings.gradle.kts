include(":app")
rootProject.name = "VitalSync"

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("com\\.android.*")
            }
        }
    }
}
