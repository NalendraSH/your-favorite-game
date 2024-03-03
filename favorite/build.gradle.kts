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
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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