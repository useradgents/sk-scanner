plugins {
    kotlin("multiplatform")
    id("tech.skot.library-viewlegacy")
    signing
}

dependencies {
    implementation("androidx.camera:camera-core:1.0.2")
    implementation("androidx.camera:camera-lifecycle:1.0.2")
    api("androidx.camera:camera-view:1.0.0-alpha32")
    api("androidx.camera:camera-camera2:1.0.2")
    implementation("com.google.mlkit:barcode-scanning:17.0.1")
}



val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

val publication = getPublication(project)
publishing {
    publications.withType<MavenPublication> {
        artifact(javadocJar.get())

        pom {
            name.set(project.name)
            description.set("${project.name} module for SK-Scanner skot library")
            url.set("https://github.com/skot-framework/sk-scanner")
            licenses {
                license {
                    name.set("Apache 2.0")
                    url.set("https://www.apache.org/licenses/LICENSE-2.0")
                }
            }
            developers {
                developer {
                    id.set("sgueniot")
                    name.set("Sylvain Guéniot")
                    email.set("sylvain.gueniot@gmail.com")
                }
                developer {
                    id.set("MathieuScotet")
                    name.set("Mathieu Scotet")
                    email.set("mscotet.lmit@gmail.com")
                }
            }
            scm {
                connection.set("scm:git:github.com/skot-framework/sk-scanner.git")
                developerConnection.set("scm:git:ssh://github.com/skot-framework/sk-scanner.git")
                url.set("https://github.com/skot-framework/sk-scanner/tree/master")
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        publication.signingKeyId,
        publication.signingKey,
        publication.signingPassword
    )
    this.sign(publishing.publications)
}