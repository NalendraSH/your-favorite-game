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
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.room:room-ktx:2.6.1")
    api("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // room
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    androidTestImplementation("androidx.room:room-testing:2.6.1")

    // encryption
    implementation("net.zetetic:android-database-sqlcipher:4.4.0")
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")
}