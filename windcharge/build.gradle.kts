import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.bukkitYml)
}

group = "dev.arnaldo.windcharge"

bukkit {
    name = "windcharge"
    prefix = "WindCharge"
    apiVersion = "1.21"
    version = "${project.version}"
    website = "arnaldo.dev"
    main = "dev.arnaldo.windcharge.WindChargePlugin"
    description = "The wind charge system"
    author = "SrBlecaute"
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    archiveFileName.set("${project.name}-${version}.jar")
}