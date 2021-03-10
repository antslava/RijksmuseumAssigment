import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

val PluginDependenciesSpec.androidApplication: PluginDependencySpec
    get() = id("com.android.application")

val PluginDependenciesSpec.kotlinAndroid: PluginDependencySpec
    get() = kotlin("android")

val PluginDependenciesSpec.kotlinKapt: PluginDependencySpec
    get() = id("org.jetbrains.kotlin.kapt")
