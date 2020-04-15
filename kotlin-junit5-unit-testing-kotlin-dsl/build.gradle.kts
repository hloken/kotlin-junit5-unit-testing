@file:Suppress("PropertyName")

val kluent_version: String by project
val junit_version: String by project
val mockk_version: String by project

plugins {
    kotlin("jvm") version "1.3.71"
    idea
    eclipse
    java
    application
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

//    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
    testImplementation("org.amshove.kluent:kluent:$kluent_version")
    testImplementation("io.mockk:mockk:$mockk_version")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events( "passed", "skipped", "failed")
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}