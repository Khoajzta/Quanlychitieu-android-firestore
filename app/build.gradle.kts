plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.quanlythuchi_android_firestore"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.quanlythuchi_android_firestore"
        minSdk = 28
        targetSdk = 36
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    //icon
    implementation("androidx.compose.material:material-icons-extended")
    //navigatinon
    implementation("androidx.navigation:navigation-compose:2.9.5")
    implementation("androidx.compose.animation:animation:1.9.3")

    //hilt

    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    kapt(libs.dagger.hilt.android.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")


    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.emoji2.bundled)


    //refresh
    implementation("androidx.compose.material:material:1.5.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // Gọi api http
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-android:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-json:2.3.1")
    implementation("com.squareup.okhttp3:logging-interceptor:5.2.1")

    // ViewModel & Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.5")

    //emoji
    implementation("androidx.emoji2:emoji2-emojipicker:1.6.0")

    //login google
    implementation("com.google.android.gms:play-services-auth:21.4.0")

    //datastore
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    //trượt
    implementation("me.saket.swipe:swipe:1.3.0")

    //load ảnh url
    implementation("io.coil-kt:coil-compose:2.7.0")


// Nếu bạn muốn dùng Compose + Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")

    implementation("com.google.guava:guava:31.1-android")
    implementation("com.google.mlkit:text-recognition:16.0.1")

    //Svg
    implementation("io.coil-kt:coil-svg:2.7.0")

// Compose permissions (hỗ trợ yêu cầu quyền)
    implementation("com.google.accompanist:accompanist-permissions:0.37.3")

    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}