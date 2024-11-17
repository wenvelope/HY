plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.room.plugin)
    alias(libs.plugins.ksp)
}


kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {

        }

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.coroutines)
            implementation(libs.kotlinx.serialization)

            implementation(libs.room.runtime)
            implementation(libs.kotlinx.datetime)

            implementation(projects.core.database)
            implementation(projects.core.dateutil)

        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}


dependencies {
    //keep in mind that i am not compiling for iOS x86, if you do need it, just add it below
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspDesktop", libs.room.compiler)
}


room {
    schemaDirectory("$projectDir/schemas")
}

