plugins {
    val kotlinPluginVersion = "2.3.20"

    id("org.springframework.boot") version "4.0.5"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.jpa") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    id("com.gorylenko.gradle-git-properties") version "2.5.2"
    groovy
}

group = "dk.cngroup.wishlist"
version = "1.3.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.14")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")
    implementation("com.google.code.gson:gson")
    implementation("net.bull.javamelody:javamelody-spring-boot4-starter:2.6.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
//    runtimeOnly("com.mysql:mysql-connector-j")
//    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")

    // dependencies for using Spock
    testImplementation("org.spockframework:spock-spring:2.4-groovy-5.0")
    testImplementation("org.hamcrest:hamcrest-core")   // only necessary if Hamcrest matchers are used
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("net.bytebuddy:byte-buddy") // allows mocking of classes (in addition to interfaces)
}

kotlin {
    jvmToolchain(25)
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
