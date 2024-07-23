
group = "dev.arnaldo"
version = "1.0.0-SNAPSHOT"

plugins {
    id("java")
    id("maven-publish")
    alias(libs.plugins.shadowjar)
}

allprojects {
    group = rootProject.group
    version = rootProject.version

    apply {
        plugin("java-library")
        plugin("maven-publish")
        plugin(rootProject.libs.plugins.shadowjar.get().pluginId)
    }

    repositories {
        mavenLocal()
        mavenCentral()

        maven("https://jitpack.io")
        maven("https://repo.codemc.io/repository/nms/")
        maven("https://repo.codemc.io/repository/nms/")
    }

    dependencies {
        compileOnly(rootProject.libs.spigot)

        rootProject.libs.lombok.let {
            compileOnly(it)
            annotationProcessor(it)
        }

        rootProject.libs.let {
            compileOnly(it.jetbrainsAnnotations)
            annotationProcessor(it.jetbrainsAnnotations)
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
}