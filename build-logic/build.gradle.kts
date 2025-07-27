plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven(url = "https://plugins.gradle.org/m2/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.gradle)
    implementation(libs.androidx.room.gradle.plugin)
}


gradlePlugin {
    plugins {
        register("kmpLibraryComposePlugin") {
            id = libs.plugins.kmp.library.compose.feature.plugin.get().pluginId
            implementationClass = "plugins.KmpLibraryComposeFeaturePlugin"
        }

        register("kmpLibrarySerializationPlugin") {
            id = libs.plugins.kmp.library.serialization.feature.plugin.get().pluginId
            implementationClass = "plugins.KmpLibrarySerializationFeaturePlugin"
        }
        register("kmpLibraryKoinPlugin") {
            id = libs.plugins.kmp.library.koin.feature.plugin.get().pluginId
            implementationClass = "plugins.KmpLibraryKoinFeaturePlugin"
        }
        register("kmpLibraryUdfPlugin") {
            id = libs.plugins.kmp.library.udf.feature.plugin.get().pluginId
            implementationClass = "plugins.KmpLibraryUdfFeaturePlugin"
        }
        register("kmpLibraryRoomPlugin") {
            id = libs.plugins.kmp.library.room.feature.plugin.get().pluginId
            implementationClass = "plugins.KmpLibraryRoomFeaturePlugin"
        }

        register("kmpApplicationComposePlugin") {
            id = libs.plugins.kmp.application.compose.feature.plugin.get().pluginId
            implementationClass = "plugins.KmpApplicationComposeFeaturePlugin"
        }
        register("kmpApplicationKoinPlugin") {
            id = libs.plugins.kmp.application.koin.feature.plugin.get().pluginId
            implementationClass = "plugins.KmpApplicationKoinFeaturePlugin"
        }

    }
}
