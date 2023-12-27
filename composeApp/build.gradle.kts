import com.android.build.api.dsl.Packaging
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.serialization)
    alias(libs.plugins.mokoRes)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosArm64(),
        // take too long to build so commented for debugging
//        iosX64(),
//        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            export("dev.icerock.moko:resources:${libs.versions.moko.res}")
            export("io.github.mirzemehdi:kmpnotifier:${libs.versions.kmpNotifier}")
            freeCompilerArgs += "-Xbinary=bundleId=tj.yakroh.yakrohapp"
        }
    }

    sourceSets {
        // workaround for the issue https://github.com/icerockdev/moko-resources/issues/606
        getByName("androidMain") {
            kotlin.srcDir("build/generated/moko/androidMain/src")
        }
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.koin.android)

            implementation(libs.ktor.android)
            implementation(libs.ktor.logger)

            implementation(libs.compressor)
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.crashlytics)
        }
        iosMain.dependencies {
            implementation(libs.ktor.ios)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)

            implementation(libs.kotlinx.date.time)

            api(libs.koin.core)
            api(libs.koin.compose)

            implementation(libs.molecule.runtime)

            api(libs.moko.resources)
            api(libs.moko.resources.compose)

            api(libs.kmpNotifier)

            implementation(libs.ktor.core)
            implementation(libs.ktor.auth)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.serialization.json)

            implementation(libs.precompose)
            implementation(libs.precompose.viewmodel)
            implementation(libs.precompose.koin)

            implementation(libs.datastore)

            implementation(libs.compose.date.picker)
            implementation(libs.photo.picker)

            implementation(libs.kamel.image)
        }
    }
}

android {
    namespace = "tj.yakroh.yakrohapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "tj.yakroh.yakrohapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            pickFirsts.add("META-INF/versions/9/previous-compilation-data.bin")
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "tj.yakroh.yakrohapp"
    multiplatformResourcesClassName = "SharedRes"
}
