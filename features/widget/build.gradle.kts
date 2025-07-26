plugins {
    alias(libs.plugins.kmp.library.serialization.feature.plugin)
    alias(libs.plugins.kmp.library.koin.feature.plugin)
    alias(libs.plugins.kmp.library.compose.feature.plugin)
}


kotlin {
    sourceSets {

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.glance.appwidget)
            implementation(libs.androidx.glance.material3)
            implementation(libs.androidx.work.runtime)
        }

        commonMain.dependencies {
            implementation(projects.designsystem)
            implementation(projects.core.viewmodel)
            implementation(projects.components.task)
            implementation(projects.core.dateutil)
            implementation(projects.components.preference)
        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}




