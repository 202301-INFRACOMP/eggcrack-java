plugins {
    application
    alias(libs.plugins.spotless)
}

group = "eggcrack"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("6.0.3")
        }
    }
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

spotless {
    java {
        googleJavaFormat()
    }
}

application {
    mainClass = "edu.eggcrack.Main"
}
