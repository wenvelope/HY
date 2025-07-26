plugins {
    alias(libs.plugins.kmp.library.serialization.feature.plugin)
    alias(libs.plugins.kmp.library.koin.feature.plugin)
    alias(libs.plugins.kmp.library.compose.feature.plugin)
}



kotlin {
    sourceSets {

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.permissions.compose)
        }

        iosMain.dependencies {
            implementation(libs.permissions.compose)
        }

        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.navigation.compose)

            implementation(libs.alert.kmp)

            implementation(projects.designsystem)
            implementation(projects.core.dateutil)
            implementation(projects.core.viewmodel)

            implementation(projects.components.task)
            implementation(projects.components.taskCategory)
            implementation(projects.features.widget)
            implementation(projects.components.preference)
        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}




