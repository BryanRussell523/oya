buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")

    }
}
allprojects {
    repositories {
        google()      //and here
        mavenCentral()

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
}