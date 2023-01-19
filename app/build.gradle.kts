plugins {
    kotlin("kapt")
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.angelstudios.followme"
    compileSdk = 33

    defaultConfig {
        applicationId ="com.angelstudios.followme"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner ="androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            resources.excludes.add("META-INF/LICENSE.md")
            resources.excludes.add("META-INF/LICENSE-notice.md")
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.compose.ui:ui:1.3.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.compose.material3:material3:1.1.0-alpha04")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.3")

    // lifeCycle compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")


    //Hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")

    // hilt navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")

    //Splash screen
    implementation("androidx.core:core-splashscreen:1.0.0")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:31.0.0"))

    // Declare the dependency for the Crashlytics library
    implementation ("com.google.firebase:firebase-crashlytics-ktx")

    implementation ("androidx.compose.material:material-icons-extended:1.3.1")



    testImplementation( "junit:junit:4.13.2")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation ("org.mockito:mockito-core:4.10.0")
    androidTestImplementation ("org.mockito:mockito-android:4.0.0")

    //For runBlockingTest, CoroutineDispatcher etc.
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:2.44.2")
    // ...with Kotlin.
    kaptTest("com.google.dagger:hilt-android-compiler:2.44.2")
    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44.2")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44.2")

    //implementation("com.google.accompanist:accompanist-permissions:0.28.0")

    implementation("com.google.accompanist:accompanist-permissions:0.28.0")



    //mockk
    testImplementation("io.mockk:mockk:1.13.3")
    androidTestImplementation("io.mockk:mockk:1.13.3")

    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation ("androidx.arch.core:core-testing:2.1.0")

    androidTestImplementation ("androidx.navigation:navigation-testing:2.5.3")

}

kapt {
    correctErrorTypes = true
}