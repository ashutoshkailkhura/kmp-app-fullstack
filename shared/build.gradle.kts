import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
//    id("com.android.library")
    id("com.squareup.sqldelight")
}

kotlin {

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
//            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    jvm()

    sourceSets {

        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.content.negotiation.client)
                implementation(libs.kotlinxCoroutinesCore)
                implementation("io.ktor:ktor-client-cio:2.3.5")
                implementation("io.ktor:ktor-client-websockets:2.3.5")
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(libs.sqldelightRuntime)
                implementation(libs.voyagerNavigator)
                implementation(libs.voyagerTabNavigator)
                implementation(libs.voyagerScreenModel)
                implementation(libs.voyagerTransitions)
//                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
                implementation(libs.mvvmCompose)
                implementation(libs.mvvmFlowCompose)
                implementation("com.darkrockstudios:mpfilepicker:3.1.0")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.sqldelightAndroid)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.sqldelightAndroid)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqldelightNative)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "org.example.project.db"
    }
    linkSqlite = true
}