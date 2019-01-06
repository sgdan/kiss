import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    antlr
    java
    application
    kotlin("jvm") version "1.3.11"
    kotlin("kapt") version "1.3.11"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

configure<JavaPluginConvention> {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("io.github.microutils:kotlin-logging:1.6.10")
    antlr("org.antlr:antlr4:4.7.1")
    compile("org.graalvm.truffle:truffle-api:1.0.0-rc10")
    kapt("org.graalvm.truffle:truffle-dsl-processor:1.0.0-rc10")

    testImplementation("org.slf4j:slf4j-simple:1.7.25")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.11")
    constraints {
        // stop complaints about conflicting versions in classpath
        testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.11")
        testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.3.11")
    }
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
