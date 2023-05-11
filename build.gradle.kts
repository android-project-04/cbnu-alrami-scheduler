import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.11"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
	kotlin("plugin.lombok") version "1.8.21"
	id("io.freefair.lombok") version "5.3.0"
	id("io.kotest.multiplatform") version "5.0.2"
}

kotlinLombok {
	lombokConfigurationFile(file("lombok.config"))
}


allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

group = "cbnu.io"
version = "5.5.5"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// Jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")

	// MySQL
	runtimeOnly("com.mysql:mysql-connector-j")

	// Crawling
	implementation("org.jsoup:jsoup:1.11.3")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("org.testcontainers:junit-jupiter:1.17.6")
	testImplementation ("org.testcontainers:mysql:1.18.1")
	testImplementation("io.kotest:kotest-runner-junit5:$version")
	testImplementation("io.kotest:kotest-assertions-core:$version")
	testImplementation("io.mockk:mockk:1.13.4")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
