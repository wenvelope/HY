plugins {
    alias( libs.plugins.kmp.library.serialization.feature.plugin)
    alias(libs.plugins.kmp.library.koin.feature.plugin)
    alias(libs.plugins.kmp.library.compose.feature.plugin)
}



kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.navigation.compose)

            implementation(libs.kotlinx.datetime)

            api(projects.designsystem)
            implementation(projects.core.viewmodel)
            implementation(projects.core.dateutil)
            implementation(projects.components.task)
            implementation(projects.components.preference)

        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


