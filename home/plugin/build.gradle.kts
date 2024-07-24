import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.bukkitYml)
}

bukkit {
    name = "home"
    prefix = "Home"
    apiVersion = "1.21"
    version = "${project.version}"
    website = "arnaldo.dev"
    main = "dev.arnaldo.home.HomePlugin"
    description = "The home system"
    author = "SrBlecaute"
}

dependencies {
    implementation(project(":home:api"))

    implementation(rootProject.libs.lamp.commom)
    implementation(rootProject.libs.lamp.bukkit)
    implementation(rootProject.libs.sqlProvider)
    implementation(rootProject.libs.hikary)
}

tasks.withType<ShadowJar> {
    dependsOn(":home:api:shadowJar")

    dependencies {
        exclude("META-INF/**", "**/*.html", "module-info.*")
        exclude(dependency("org.slf4j:slf4j-api"))
        exclude(dependency("org.xerial:sqlite-jdbc"))
        exclude(dependency("mysql:mysql-connector-java"))
        exclude(dependency("com.mysql:mysql-connector-j"))
        exclude(dependency("com.google.protobuf:protobuf-java"))
        exclude(dependency("org.mariadb.jdbc:mariadb-java-client"))
        exclude(dependency("com.github.waffle:waffle-jna"))
        exclude(dependency("net.java.dev.jna:.*"))
        exclude(dependency("ben-manes.caffeine:caffeine"))
    }

    val projectName = project.parent?.name ?: "unknown"

    archiveClassifier.set("")
    archiveFileName.set("${projectName}-${version}.jar")
}