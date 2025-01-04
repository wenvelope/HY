rootProject.name = "HY"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
}

include(":composeApp")

include(":core:viewmodel")
include(":core:dateutil")
include(":core:database")
include(":core:datastore")
include(":core:network")


include(":designsystem")

include(":features:login")
include(":features:account")
include(":features:setting")
include(":features:home")
include(":features:today")
include(":features:taskcreation")
include(":features:search")
include(":features:widget")
include(":features:pomodoro")

include(":components:task")
include(":components:auth")
include(":components:taskCategory")
include(":components:preference")
include(":components:user")






