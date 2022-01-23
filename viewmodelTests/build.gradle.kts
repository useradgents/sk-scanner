plugins {
    id("java-library")
    kotlin("multiplatform")
    signing
}


kotlin {
    jvm("jvm")

    sourceSets {
        val jvmMain by getting {

            kotlin.srcDir("src/jvmMain/kotlin")

            dependencies {
                implementation(project(":viewmodel"))
                implementation("${Versions.frameworkGroup}:viewmodelTests:${Versions.framework}")
                implementation("${Versions.frameworkGroup}:core:${Versions.framework}")
            }
        }
    }
}

if (!localPublication) {
    val publication = getPublication(project)

    val javadocJar by tasks.registering(Jar::class) {
        archiveClassifier.set("javadoc")
    }

    publishing {
        publications {
            publications.withType<MavenPublication> {
                artifact(javadocJar.get())

                pom {
                    name.set(project.name)
                    description.set("${project.name}  module for SK-Scanner skot library")
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

    }


    signing {
        useInMemoryPgpKeys(
                publication.signingKeyId,
                publication.signingKey,
                publication.signingPassword
        )
        this.sign(publishing.publications)
    }
}




