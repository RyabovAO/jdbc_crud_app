plugins {
    id("java")
}

group = "com.aleksey"
version = "1.0"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.36")
    implementation("com.mysql:mysql-connector-j:9.2.0")
    implementation("com.google.protobuf:protobuf-java:4.28.3")
    implementation("org.liquibase:liquibase-core:4.30.0")
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.12.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0")
}

tasks.test {
    useJUnitPlatform()
}