import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    antlr
    java
    application
    kotlin("jvm") version "1.3.60"
    kotlin("kapt") version "1.3.60"
}

repositories {
    mavenCentral()
    jcenter()
}

configure<JavaPluginConvention> {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.github.microutils:kotlin-logging:1.7.7")
    antlr("org.antlr:antlr4:4.7.2")
    compile("org.graalvm.truffle:truffle-api:19.2.1")
    kapt("org.graalvm.truffle:truffle-dsl-processor:19.2.1")
    compile("org.graalvm.sdk:graal-sdk:19.2.1")

    testImplementation("org.slf4j:slf4j-simple:1.7.29")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
}

application {
    mainClassName = "com.github.sgdan.kiss.MainKt"
    applicationDefaultJvmArgs = listOf("-XX:-UseJVMCIClassLoader")
}

tasks.generateGrammarSource {
    arguments = listOf("-visitor", "-long-messages")
}

tasks.compileKotlin {
    dependsOn(tasks.generateGrammarSource)
}

tasks.test {
    useJUnitPlatform()

    /*
     * See http://www.graalvm.org/docs/reference-manual/languages/jvm/
     *
     * "Disables the class loader used to isolate JVMCI and Graal from
     * application code. This is useful if you want to programmatically
     * invoke Graal."
     */
    jvmArgs = listOf("-XX:-UseJVMCIClassLoader")
}
