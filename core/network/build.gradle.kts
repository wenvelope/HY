plugins {
    id("kmp-library-plugin")
}


kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

        }

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.coroutines)
        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


