object Dependencies {

    object Versions {
        const val targetSdk = 30
        const val compileSdk = 30
        const val minSdk = 23
        const val buildTools = "30.0.3"

        const val kotlin = "1.4.31"

        const val koin = "2.2.2"

        const val leakCanary = "2.6"

        const val appcompat = "1.2.0"
        const val androidXCore = "1.3.2"
        const val androidXLifecycle = "2.3.0"
        const val material = "1.3.0"
        const val constraint = "2.0.4"
        const val recyclerView = "1.1.0"

        const val glide = "4.12.0"

        const val moshi = "1.11.0"
        const val retrofit = "2.9.0"
        const val okhttp = "4.9.1"

        const val junit = "4.13.2"
        const val truth = "1.1.2"
        const val testCore = "1.3.0"
        const val espresso = "3.3.0"
        const val liveDataTesting = "1.1.2"
        const val archTestCore = "2.1.0"
        const val mockitoCore = "3.5.13"
        const val coroutinesTest = "1.4.2"
        const val androidXJunit = "1.1.2"

        const val agp = "4.1.2"
    }

    object Classpath {
        const val gradlePlugin = "com.android.tools.build:gradle:${Versions.agp}"
        const val kotlinGradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Libraries {
        const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

        const val material = "com.google.android.material:material:${Versions.material}"

        const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val liveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidXLifecycle}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidXLifecycle}"
        const val recyclerview =
            "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        const val androidCore = "androidx.core:core-ktx:${Versions.androidXCore}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
        const val lifecycleCommon =
            "androidx.lifecycle:lifecycle-common-java8:${Versions.androidXLifecycle}"
        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidXLifecycle}"

        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
        const val glideOkHttp = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"

        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

        const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
        const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
        const val koinAndroidExt = "org.koin:koin-android-ext:${Versions.koin}"
        const val koinTest = "org.koin:koin-test:${Versions.koin}"

        const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        const val moshiCompiler = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

        const val junit = "junit:junit:${Versions.junit}"
        const val truth = "com.google.truth:truth:${Versions.truth}"
        const val liveDataTesting = "com.jraska.livedata:testing-ktx:${Versions.liveDataTesting}"
        const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archTestCore}"
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
        const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
        const val androidTestTruth = "androidx.test.ext:truth:${Versions.testCore}"
        const val androidTestRules = "androidx.test:rules:${Versions.testCore}"
        const val androidTestRunner = "androidx.test:runner:${Versions.testCore}"
        const val androidCoreTesting = "androidx.arch.core:core-testing:${Versions.archTestCore}"
        const val androidTestJUnit = "androidx.test.ext:junit:${Versions.androidXJunit}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    }
}
