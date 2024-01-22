plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "br.com.ivan.ninjaflixmvvm"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.ivan.ninjaflixmvvm"
        minSdk = 28
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    val coroutinesVersion = "1.7.3"
    val retrofitVersion = "2.9.0"
    val lifeCycleVersion = "2.6.2"
    val daggerHiltVersion = "2.48"
    val hiltLifeCycleVersion = "1.1.0"
    val coilVersion = "1.2.2"
    val pagingVersion = "3.2.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$coroutinesVersion")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation ("com.squareup.retrofit2:converter-scalars:$retrofitVersion")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycleVersion")
    // implementation ("androidx.lifecycle:lifecycle-extensions:$lifeCycleVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifeCycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycleVersion")

    implementation ("androidx.activity:activity-ktx:1.8.2")
    //implementation ("androidx.fragment:fragment-ktx:1.8.2")


    //Hilt for di
    implementation ("com.google.dagger:hilt-android:$daggerHiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$daggerHiltVersion")
    //implementation ("androidx.hilt:hilt-lifecycle-viewmodel:$daggerHiltVersion")
    //kapt ("androidx.hilt:hilt-compiler:$daggerHiltVersion")
    //implementation ("androidx.hilt:hilt-navigation-compose:$daggerHiltVersion")
    

    //Navigation
    val nav_version = "2.7.6"
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Room
    val room_version = "2.6.1"
    implementation ("androidx.room:room-runtime:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    kapt  ("androidx.room:room-compiler:$room_version")

    //Pagging
    val paging_version = "3.2.1"
    //implementation ("androidx.paging:paging-runtime:$paging_version")
    implementation ("androidx.paging:paging-runtime-ktx:$paging_version")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    kapt ("com.github.bumptech.glide:compiler:4.11.0")

    //Timber
    implementation ("com.jakewharton.timber:timber:4.7.1")

    //coil
    implementation("io.coil-kt:coil:2.2.2")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-firestore")

    //cicle image
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //slider
    implementation ("com.github.venky97vp:android-image-slider:1.1.0")
    //implementation("com.github.smarteist:autoimageslider:1.4.0")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")

    //Youtube vídeos
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

}