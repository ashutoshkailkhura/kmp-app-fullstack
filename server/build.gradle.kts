plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
    application
}

group = "org.example.project"
version = "1.0.0"
application {
    mainClass.set("org.example.project.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

val exposed_version: String by project
val sshAntTask = configurations.create("sshAntTask")

dependencies {
    implementation(projects.shared)

    implementation(libs.logback)

    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.content.negotiation.server)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.websockets.jvm)

    testImplementation(libs.ktor.server.tests.jvm)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation(libs.exposed.kotlin.datetime)

    implementation(libs.mysql.connector.java)

    implementation(libs.gson)

    implementation(libs.commons.codec)
    sshAntTask(libs.ant.jsch)

}