plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("org.jmailen.kotlinter")
    id("kotlin-parcelize")
    `maven-publish`
}

android {
    namespace = "com.glorfindel.tehanu"
    compileSdk = 35
    buildToolsVersion = "35.0.0"
    defaultConfig.minSdk = 28
    kotlin.jvmToolchain(17)
    buildFeatures.compose = true
    buildTypes.getByName("release").isMinifyEnabled = false
    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

    androidComponents {
        beforeVariants(selector().withBuildType("debug")) { variantBuilder ->
            variantBuilder.enable = false
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.runtime:runtime-livedata")

    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("androidx.work:work-runtime-ktx:2.9.1")
}

afterEvaluate {
    publishing {
        publications {
            register("release", MavenPublication::class) {
                from(components["release"])
                groupId = "com.glorfindel.tehanu"
                artifactId = "tehanu"
                version = "1.0.4"
            }
        }
    }
}
