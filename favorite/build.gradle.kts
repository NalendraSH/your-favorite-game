plugins {
    id("com.android.dynamic-feature")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
}

apply {
    from("../shared_dependencies.gradle")
}

android {
    namespace = "id.nanz.yourfavoritegame.favorite"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            proguardFiles("proguard-rules-dynamic-features.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // module
    implementation(project(":app"))
    implementation(project(":core"))
}