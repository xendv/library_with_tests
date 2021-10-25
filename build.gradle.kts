plugins {
    java
    application
}

group = "org.xendv.java.edumail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allprojects{
    apply(plugin = "java")
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        // annotations
        implementation ("org.jetbrains:annotations:13.0")
        implementation("org.projectlombok:lombok:1.18.4")
        annotationProcessor("org.projectlombok:lombok:1.18.4")

        // injector
        implementation("com.google.inject:guice:5.0.1")

        // parsing
        implementation("com.google.code.gson:gson:2.8.8")

        // testing
        testImplementation("org.mockito:mockito-core:2.24.0")

        testImplementation("junit:junit:4.12")

        testImplementation("org.hamcrest:hamcrest-all:1.3")
    }
}

dependencies {
    // modules
    implementation(project(":models"))
    implementation(project(":controller"))
    //testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    //testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"]="library.with.tests.Application";
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

task<JavaExec>("execute") {
    mainClass.set("library.with.tests.Application");
    classpath = java.sourceSets["main"].runtimeClasspath
    args("./books.txt", "101")
    standardInput = System.`in`
}