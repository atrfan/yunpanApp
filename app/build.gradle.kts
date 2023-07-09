plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.atrfan.yunpanapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.atrfan.yunpanapp"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        viewBinding =true
        dataBinding = true
    }
}

dependencies {
    implementation ("com.github.bumptech.glide:glide:4.15.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    val dialogx_version = "0.0.48"
    implementation ("com.github.kongzue.DialogX:DialogX:${dialogx_version}")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation ("com.squareup.retrofit2:retrofit:2.6.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.6.1")

    val nav_version = "2.6.0"
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")

    implementation ("com.google.android.material:material:1.4.0")

    implementation ("androidx.fragment:fragment-ktx:1.6.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}