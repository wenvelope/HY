plugins {
    alias(libs.plugins.kmp.library.koin.feature.plugin)
}


kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.data.store.core)
            implementation(projects.core.network)
            implementation(projects.components.preference)
        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

