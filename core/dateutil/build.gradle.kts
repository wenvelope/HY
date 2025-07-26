plugins {
    alias(libs.plugins.kmp.library.koin.feature.plugin)
    alias(libs.plugins.compose.compiler)
}


kotlin {
    sourceSets {
        commonMain.dependencies {
           api(libs.kotlinx.datetime)
        }
    }
}



