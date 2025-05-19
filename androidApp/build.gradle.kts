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
//                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")

                implementation(libs.mvvmCompose)
                implementation(libs.mvvmFlowCompose)

                implementation("com.darkrockstudios:mpfilepicker:3.1.0")
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


