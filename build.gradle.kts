val queryDslVersion = "5.1.0"

plugins {
    java
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.beom"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //spring boot core
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    //spring security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("io.jsonwebtoken:jjwt-api:0.12.7")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.7")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.7")
    // DTO ↔ Entity Mapping
    implementation("org.modelmapper:modelmapper:3.1.0")

    // Swagger (OpenAPI)
    // API Documentation(Swagger)
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")

    //JSON
    implementation("com.google.code.gson:gson:2.13.1")
    // Database
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("com.h2database:h2")
    testRuntimeOnly("com.h2database:h2")                 // 테스트환경용 H2 인메모리

    //jpa
    implementation ("com.querydsl:querydsl-jpa:${queryDslVersion}")
    annotationProcessor("javax.persistence:javax.persistence-api")
    annotationProcessor("javax.annotation:javax.annotation-api")
    annotationProcessor("com.querydsl:querydsl-apt:$queryDslVersion:jpa")


    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

sourceSets {
    named("main") {
        java {
            srcDir("$projectDir/src/main/java")
            srcDir("$projectDir/build/generated")
        }
    }
    named("test"){
        java{
            srcDir("$projectDir/src/test/java")
        }
    }
}


tasks.named<Test>("test") {
    useJUnitPlatform()
    systemProperty("spring.profiles.active", "test")
    testLogging {
        events("passed", "skipped", "failed")
    }
    include("**/*Test.class", "**/*Tests.class", "**/*IT.class")
}

