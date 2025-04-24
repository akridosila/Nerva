plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.realnerva"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.realnerva"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packagingOptions {
        exclude("lib/arm64-v8a/libfilament-jni.so")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation("com.google.android.material:material:1.9.0")
    implementation(libs.sceneform.ux) {
        exclude(group = "com.android.support", module = "support-compat")
    }
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.google.ar.sceneform.ux:sceneform-ux:1.17.1") {
        exclude(group = "com.android.support", module = "support-compat")
    }
    implementation("com.google.ar:core:1.39.0")
}