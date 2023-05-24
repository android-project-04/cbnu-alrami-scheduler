import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.11"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("io.kotest.multiplatform") version "5.0.2"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
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
//	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")


	// Jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")

	// MySQL
	runtimeOnly("com.mysql:mysql-connector-j")

	implementation ("org.springframework.boot:spring-boot-starter-data-redis")

	// Crawling
	implementation("org.jsoup:jsoup:1.15.4")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("org.testcontainers:junit-jupiter:1.17.6")
	testImplementation ("org.testcontainers:mysql:1.18.1")
	testImplementation("io.kotest:kotest-runner-junit5:$version")
	testImplementation("io.kotest:kotest-assertions-core:$version")
//	testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:$version")
	testImplementation("io.mockk:mockk:1.13.4")
	implementation("it.ozimov:embedded-redis:0.7.3") {
		exclude("org.slf4j", "slf4j-simple")
	}

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

configurations {
	all {
		exclude("org.springframework.boot:spring-boot-starter-logging")
		exclude("ch.qos.logback:logback-classic:1.2.12")
		exclude("org.apache.logging.log4j:log4j-to-sl4j")
		exclude("org.springframework.boot:spring-boot-starter-logging")
		exclude("org.jboss.logging:jboss-logging:3.4.3")
		exclude("org.slf4j:sl4j-log4j12")
		exclude("log4j:log4j")
		exclude("org.slf4j", "slf4j-simple")
		exclude("org.apache.logging.log4j", "log4j-slf4j-impl")
	}
}
