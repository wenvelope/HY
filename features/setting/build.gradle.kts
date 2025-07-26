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

            api(libs.filekit.compose)
            api(libs.filekit.core)


            implementation(libs.ktor.client.core)


            implementation(projects.designsystem)
            implementation(projects.components.taskCategory)
            implementation(projects.core.viewmodel)
            implementation(projects.components.preference)
            implementation(projects.components.user)
            implementation(projects.components.auth)
        }



        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)

        }
    }
}



