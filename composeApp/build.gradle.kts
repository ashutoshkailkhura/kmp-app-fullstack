import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
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
            baseName = "ComposeApp"
//            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(projects.shared)
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

                implementation(libs.mvvmCompose)
                implementation(libs.mvvmFlowCompose)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)

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
    }
}

android {
    namespace = "org.example.project"
    compileSdkVersion(libs.versions.android.compileSdk.get().toInt())

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        implementation(project(":shared"))
        debugImplementation(libs.compose.ui.tooling)
        implementation(libs.kotlinxCoroutinesAndroid)
    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "org.example.project.db"
    }
    linkSqlite = true
}


