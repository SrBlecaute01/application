import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    implementation(project(":home:common"))
}

tasks.withType<ShadowJar> {
    dependsOn(":home:common:shadowJar")
}