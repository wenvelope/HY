plugins {
    alias(libs.plugins.kmp.library.koin.feature.plugin)
}


kotlin {
    sourceSets {

        commonMain.dependencies {
            implementation(libs.androidx.data.store.core)
        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


