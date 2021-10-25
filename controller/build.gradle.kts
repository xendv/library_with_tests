plugins {
    java
}

group = "org.xendv.java.edumail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    //implementation("org.projectlombok:lombok:1.18.4")
    //annotationProcessor("org.projectlombok:lombok:1.18.4")

    implementation(project(":models"))
    //testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    //testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
/*
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}*/