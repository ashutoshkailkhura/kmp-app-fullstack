import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlinMultiplatform)
//    alias(libs.plugins.androidApplication)
    alias(libs.plugins.serialization)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    targetHierarchy.default()

    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {

        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.content.negotiation.client)
                implementation(libs.ktor.client.core)
                implementation(libs.kotlinxCoroutinesCore)

//                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
//                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

    }

}

