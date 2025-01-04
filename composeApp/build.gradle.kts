import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("kmp-app-plugin")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.androidx.core.splashscreen)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.navigation.compose)
            implementation(libs.animation)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.coroutines)
            implementation(libs.koin.compose.viewmodel)


            implementation(libs.coil.network.ktor)
            implementation(libs.coil.mp)
            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)

            implementation(projects.features.login)
            implementation(projects.features.setting)
            implementation(projects.features.home)
            implementation(projects.features.today)
            implementation(projects.features.taskcreation)
            implementation(projects.features.search)
            implementation(projects.features.pomodoro)

            implementation(projects.components.taskCategory)
            implementation(projects.components.task)
            implementation(projects.components.preference)
            implementation(projects.components.auth)
            implementation(projects.components.user)
            
            implementation(projects.core.database)
            implementation(projects.core.datastore)
            implementation(projects.core.network)

            implementation(projects.features.widget)

        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.hys.hy.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.hys.hy"
            packageVersion = "1.0.0"
            includeAllModules = true
            linux {
                modules("jdk.security.auth")
            }
            //新添加
            buildTypes.release{
                proguard {
                    isEnabled = false
                    configurationFiles.from("compose-desktop.pro")
                }
            }
        }
    }
}
