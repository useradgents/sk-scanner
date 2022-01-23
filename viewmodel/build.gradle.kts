plugins {
    kotlin("multiplatform")
    id("tech.skot.library")
    signing
}


tasks.dokkaHtmlPartial.configure {
    suppressInheritedMembers.set(true)
}

tasks.dokkaGfmPartial.configure {
    suppressInheritedMembers.set(true)
}

val dokkaOutputDir = "$buildDir/dokka"

tasks.getByName<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml") {
    outputDirectory.set(file(dokkaOutputDir))
}

val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
    delete(dokkaOutputDir)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
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