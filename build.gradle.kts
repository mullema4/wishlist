import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    val kotlinPluginVersion = "1.9.24"

    id("org.springframework.boot") version "3.2.6"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.jpa") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    id("com.gorylenko.gradle-git-properties") version "2.4.1"
    groovy
}

group = "dk.cngroup.wishlist"
version = "1.1.0'"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.24")
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.9")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi:2.5.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("com.google.code.gson:gson")
    implementation("com.maciejwalkowiak.spring:spring-boot-startup-report:0.2.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
//    runtimeOnly("com.mysql:mysql-connector-j")
//    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("net.bull.javamelody:javamelody-spring-boot-starter:2.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // dependencies for using Spock
    testImplementation("org.spockframework:spock-spring:2.4-M4-groovy-4.0")
    testImplementation("org.hamcrest:hamcrest-core:2.2")   // only necessary if Hamcrest matchers are used
    testRuntimeOnly("net.bytebuddy:byte-buddy:1.14.17") // allows mocking of classes (in addition to interfaces)
}

kotlin {
    jvmToolchain(17)
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

springBoot {
    buildInfo()
}

tasks.named<BootJar>("bootJar") {
    archiveFileName.set("wishlist.jar")
}

tasks.withType<Test> {
    useJUnitPlatform()
}