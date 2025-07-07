plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.android.application)
}

android {
    compileSdk = 35
    namespace = "com.bytewise.vitalsync"

    buildFeatures {
        compose = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    defaultConfig {
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        applicationId = "com.bytewise.vitalsync"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    debugImplementation(libs.ui.tooling)
    implementation(libs.compose.material)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    implementation(libs.ui.tooling.preview)
    implementation(libs.coroutines.android)
    implementation(libs.wear.tooling.preview)
    debugImplementation(libs.ui.test.manifest)
    implementation(platform(libs.compose.bom))
    implementation(libs.play.services.wearable)
    implementation(libs.material.icons.extended)
    androidTestImplementation(libs.ui.test.junit4)
    implementation(libs.lifecycle.viewmodel.compose)
    androidTestImplementation(platform(libs.compose.bom))
}