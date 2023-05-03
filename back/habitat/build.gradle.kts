plugins {
	java
	id("org.springframework.boot") version "2.7.11"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.ssafy"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	runtimeOnly("com.mysql:mysql-connector-j")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	//for S3
	implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")

	//for Swagger
	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")

	//for Security, JWT
//	implementation("org.springframework.boot:spring-boot-starter-security:2.6.7")
//	implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
//	implementation ("io.jsonwebtoken:jjwt-jackson:0.11.2")
//	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.2")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
