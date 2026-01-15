plugins {
    kotlin("multiplatform")
    id("tech.skot.library-viewlegacy")
    signing
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
    }
    androidTarget("android") {

    }
}

android {
    namespace = "tech.skot.libraries.skscanner.viewlegacy"
}

dependencies {
    implementation("androidx.camera:camera-core:1.5.2")
    implementation("androidx.camera:camera-lifecycle:1.5.2")
    api("androidx.camera:camera-view:1.5.2")
    api("androidx.camera:camera-camera2:1.5.2")
    implementation("com.google.mlkit:barcode-scanning:17.3.0")
}


val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}
