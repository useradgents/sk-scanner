plugins {
    kotlin("multiplatform")
    id("tech.skot.library-contract")
    signing
}


val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}
