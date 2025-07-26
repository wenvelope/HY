plugins {
    alias(libs.plugins.kmp.library.serialization.feature.plugin)
    alias(libs.plugins.kmp.library.koin.feature.plugin)
    alias(libs.plugins.kmp.library.compose.feature.plugin)
}


kotlin {
    sourceSets {

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            api(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.navigation.compose)

            api(libs.coil.network.ktor)
            api(libs.coil.mp)
            api(libs.coil.compose.core)
            api(libs.coil.compose)

            implementation(projects.components.user)
        }


        iosMain.dependencies {
            api(libs.ktor.client.darwin)
        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)

            api(libs.ktor.client.okhttp)
        }
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}