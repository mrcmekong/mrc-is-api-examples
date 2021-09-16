plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
    id("groovy")
    id("org.jetbrains.kotlin.kapt") version "1.5.30"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.micronaut.application") version "2.0.5"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.30"
}

version = "0.1"
group = "com.example"

val kotlinVersion = project.properties["kotlinVersion"]
repositories {
    mavenCentral()
}

micronaut {
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("org.mrcmekong.*")
    }
}

dependencies {
    kapt("info.picocli:picocli-codegen")
    kapt("io.micronaut:micronaut-http-validation")
    implementation("info.picocli:picocli")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.picocli:micronaut-picocli")
    implementation("javax.annotation:javax.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

}


application {
    mainClass.set("org.mrcmekong.ApiToCsvCliCommand")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

}
