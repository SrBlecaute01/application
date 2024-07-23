import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

subprojects {
    group = "${rootProject.group}.home"
    version = rootProject.version
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    archiveFileName.set("${project.name}-${version}.jar")
}