plugins {
    val kotlinPluginVersion = "2.0.20"

    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.jpa") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    id("com.gorylenko.gradle-git-properties") version "2.4.2"
    groovy
}

group = "dk.cngroup.wishlist"
version = "1.1.0'"

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly("org.jetbrains.kotlin:kotlin-bom:2.0.20")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi:2.6.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
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
    testImplementation("org.hamcrest:hamcrest-core")   // only necessary if Hamcrest matchers are used
    testRuntimeOnly("net.bytebuddy:byte-buddy") // allows mocking of classes (in addition to interfaces)
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

tasks {
    bootJar {
        archiveFileName.set("wishlist.jar")
    }
    test {
        useJUnitPlatform()
    }
}