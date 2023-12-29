plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.oya"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.oya"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
    android {
        buildFeatures {
            dataBinding = true
            viewBinding = true
        }
    }
}

dependencies {
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
   //implementation("com.google.firebase:firebase-auth:19.3.2")
    implementation("com.google.firebase:firebase-auth:22.3.0")

    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation("com.google.firebase:firebase-firestore-ktx:24.7.1")
    implementation("com.google.firebase:firebase-storage-ktx:20.2.1")
    implementation ("com.google.firebase:firebase-database:20.2.2")
    implementation("com.google.firebase:firebase-messaging:23.3.1")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.android.support:design:27.1.1")


    implementation ("com.firebaseui:firebase-ui-firestore:8.0.0")


    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.3")
    implementation("androidx.navigation:navigation-ui:2.7.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.hbb20:ccp:2.5.1")

    implementation("com.squareup.picasso:picasso:2.71828")
    //DISPLAY USER IMAGE FROM DATABASE
    implementation("com.github.bumptech.glide:glide:4.8.0")
    //FLOATING ACTION BUTTON
    implementation("com.google.android.material:material:1.1.0")

    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.appcompat:appcompat:1.5.0")
    //LOADING BUTTON ANIMATION(LOTTIE ANIMATION)
    implementation ("com.airbnb.android:lottie:5.2.0")
    /**
     * dependency to request the runtime permissions.
     */
    implementation ("com.karumi:dexter:4.2.0")
    implementation("androidx.browser:browser:1.3.0")
    implementation ("com.google.android.play:integrity:1.3.0")
}