import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.bukkitYml)
}

bukkit {
    name = "home"
    prefix = "Home"
    apiVersion = "21"
    version = "${project.version}"
    website = "arnaldo.dev"
    main = "dev.arnaldo.HomePlugin"
    description = "The home system"
    author = "SrBlecaute"
}

dependencies {
    implementation(rootProject.libs.lamp.commom)
    implementation(rootProject.libs.lamp.bukkit)
    implementation(rootProject.libs.sqlProvider)
    implementation(rootProject.libs.inventoryHelper)
    implementation(rootProject.libs.caffeine)
}

tasks.withType<ShadowJar> {
    dependsOn(":home:api:shadowJar")

    archiveClassifier.set("")
    archiveFileName.set("${rootProject.name}-${version}.jar")
}