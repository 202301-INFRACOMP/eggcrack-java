plugins {
    application
    alias(libs.plugins.spotless)
    alias(libs.plugins.jlink)
}

group = "edu.eggcrack"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("6.0.3")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-Xlint:unchecked"))
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
    kotlinGradle {
        ktlint()
    }
}

application {
    mainClass = "edu.eggcrack.App"
    mainModule = "edu.eggcrack"
}

jlink {
    options = listOf("--strip-debug", "--compress", "zip-6", "--no-header-files", "--no-man-pages")
    imageName = "eggcrack"

    launcher {
        name = "eggcrack"
    }

    jpackage {
        vendor = "orpheezt"
        icon = file("../icon/eggcrack.png").absolutePath
    }
}
