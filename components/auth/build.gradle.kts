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


            implementation(libs.androidx.data.store.core)
            implementation(projects.core.network)
            implementation(projects.components.preference)

        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

