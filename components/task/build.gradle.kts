plugins {
    alias(libs.plugins.kmp.library.serialization.feature.plugin)
    alias(libs.plugins.kmp.library.koin.feature.plugin)
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)

            implementation(projects.core.database)
            implementation(projects.core.dateutil)
        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}
