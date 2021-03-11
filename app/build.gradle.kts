import Dependencies.Libraries
import Dependencies.Versions
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

plugins {
    androidApplication
    kotlinAndroid
    kotlinKapt
    detekt
}

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion(Versions.buildTools)

    defaultConfig {
        applicationId = "org.antmobile.rijksmuseum"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            minifyEnabled(false)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

configure<DetektExtension> {
    config = files("../config/detekt/detekt.yml")
}

dependencies {
    implementation(Libraries.kotlinStd)
    implementation(Libraries.androidCore)
    implementation(Libraries.appCompat)
    implementation(Libraries.material)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.liveData)
    implementation(Libraries.viewModel)
    implementation(Libraries.recyclerview)

    //Glide
    implementation(Libraries.glide) {
        exclude(group = "com.android.support")
    }
    kapt(Libraries.glideCompiler)
    implementation(Libraries.glideOkHttp)

    //Dependency Injection
    implementation(Libraries.koinAndroid)
    implementation(Libraries.koinViewModel)
    implementation(Libraries.koinAndroidExt)

    implementation(Libraries.moshi)
    kapt(Libraries.moshiCompiler)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitMoshi)
    implementation(Libraries.okHttp)
    implementation(Libraries.okHttpLogging)

    //Testing
    testImplementation(Libraries.junit)
    testImplementation(Libraries.truth)
    testImplementation(Libraries.liveDataTesting)
    testImplementation(Libraries.archCoreTesting)
    testImplementation(Libraries.coroutinesTest)
    testImplementation(Libraries.mockitoCore)

    androidTestImplementation(Libraries.koinTest)
    androidTestImplementation(Libraries.androidTestTruth)
    androidTestImplementation(Libraries.androidTestRules)
    androidTestImplementation(Libraries.androidTestRunner)
    androidTestImplementation(Libraries.androidCoreTesting) {
        exclude(group = "org.mockito", module = "mockito-core")
    }
    androidTestImplementation(Libraries.androidTestJUnit)
    androidTestImplementation(Libraries.espressoCore)
}
