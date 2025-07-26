import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kmp.application.compose.feature.plugin)
    alias(libs.plugins.kmp.application.koin.feature.plugin)
}

kotlin {
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.androidx.core.splashscreen)

        }
        commonMain.dependencies {
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.navigation.compose)

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
