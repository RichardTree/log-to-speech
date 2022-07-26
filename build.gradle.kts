plugins {
    java
    `maven-publish`
    id("org.unbroken-dome.xjc") version "2.0.0"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

xjc {
    xjcVersion.set("3.0")
}

dependencies {
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    annotationProcessor ("org.immutables:value:2.7.3")
    implementation("org.springframework:spring-core:5.3.13")
    implementation("jakarta.activation:jakarta.activation-api:2.1.0")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.0")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.google.cloud:google-cloud-texttospeech:2.2.1")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("org.springframework:spring-web:5.3.13")
    implementation("org.springframework:spring-webmvc:5.3.13")
    implementation("io.vavr:vavr:0.10.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:2.6.0")
    runtimeOnly("com.sun.xml.bind:jaxb-impl:4.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    compileOnly("javax.servlet:javax.servlet-api:3.1.0")
    compileOnly("org.immutables:value:2.9.0")
}

group = "com.rickyt"
version = "0.0.1-SNAPSHOT"
description = "log-to-speech"
java.sourceCompatibility = JavaVersion.VERSION_18


publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
