import org.gradle.internal.os.OperatingSystem

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
    jvmArgs = listOf("-Dfile.encoding=UTF-8")
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
        val os = OperatingSystem.current()
        val iconPath =
            if (os.isWindows) {
                "../icon/eggcrack.ico"
            } else if (os.isMacOsX) {
                "../icon/eggcrack.icns"
            } else if (os.isLinux) {
                "../icon/eggcrack.png"
            } else {
                throw GradleException("Unsupported operating system: ${os.name}")
            }
        icon = file(iconPath).absolutePath
        if (os.isWindows) {
            installerOptions = listOf("--win-per-user-install", "--win-dir-chooser", "--win-menu", "--win-shortcut")
            imageOptions = listOf("--win-console")
        }
        if (os.isLinux) {
            installerOptions =
                listOf(
                    "--linux-shortcut",
                    "--linux-menu-group",
                    "Utility",
                    "--linux-rpm-license-type",
                    "MIT",
                )
        }
    }
}
