plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")
}

apply {
    from("../shared_dependencies.gradle")
}

android {
    namespace = "id.nanz.yourfavoritegame.core"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val host = "api.rawg.io"
        buildConfigField("String", "HOST", "\"$host\"")
        buildConfigField("String", "BASE_URL", "\"https://$host/api/\"")
        buildConfigField("String", "ACCESS_TOKEN", "\"c0cdb47b865d45e494a789af81bca08e\"")
        buildConfigField("String", "ROOM_PW", "\"TkFMRU5EUkE=\"")
        buildConfigField("String", "CERT_PINNER_1", "\"sha256/KgyOSpsq6+nlxUBonR1zCRB7+Fg5tEsMluevNjtOGcY=\"")
        buildConfigField("String", "CERT_PINNER_2", "\"sha256/81Wf12bcLlFHQAfJluxnzZ6Frg+oJ9PWY/Wrwur8viQ=\"")
        buildConfigField("String", "CERT_PINNER_3", "\"sha256/hxqRlPTu1bMS/0DITB1SSu0vd4u/8l8TjPgfaAp63Gc=\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = true
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
        buildConfig = true
    }
}

dependencies {
    // retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.convert.gson)
    implementation(libs.okhttp.logging.interceptor)

    // coroutine
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    api(libs.androidx.lifecycle.livedata.ktx)

    // room
    ksp(libs.androidx.room.ksp)
    implementation(libs.androidx.room.ktx)
    androidTestImplementation(libs.androidx.room.testing)

    // encryption
    implementation(libs.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)
}