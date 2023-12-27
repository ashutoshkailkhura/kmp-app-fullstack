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
    implementation(libs.logback)

    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.content.negotiation.server)
    implementation(libs.ktor.serialization)
    implementation("io.ktor:ktor-server-auth:2.3.5")
    implementation("io.ktor:ktor-server-auth-jwt:2.3.5")
    implementation("io.ktor:ktor-server-call-logging:2.3.5")
    implementation("io.ktor:ktor-server-websockets-jvm")

    testImplementation("io.ktor:ktor-server-tests-jvm")

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation ("org.jetbrains.exposed:exposed-kotlin-datetime:0.44.1")

    implementation("mysql:mysql-connector-java:5.1.6")

    implementation("com.google.code.gson:gson:2.8.8")

    implementation("commons-codec:commons-codec:1.15")
    sshAntTask("org.apache.ant:ant-jsch:1.10.12")

//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}