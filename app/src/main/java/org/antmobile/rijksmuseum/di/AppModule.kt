package org.antmobile.rijksmuseum.di

import android.content.Context
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.antmobile.rijksmuseum.BuildConfig
import org.antmobile.rijksmuseum.app.converters.ErrorConverter
import org.antmobile.rijksmuseum.app.converters.ErrorConverterImpl
import org.antmobile.rijksmuseum.app.details.ArtDetailViewModel
import org.antmobile.rijksmuseum.app.list.ArtListUiItemMerger
import org.antmobile.rijksmuseum.app.list.ArtListViewModel
import org.antmobile.rijksmuseum.data.remote.LanguageProvider
import org.antmobile.rijksmuseum.data.remote.RijksMuseumApi
import org.antmobile.rijksmuseum.data.remote.datasource.ArtsRemoteDataSourceImpl
import org.antmobile.rijksmuseum.data.remote.network.AuthInterceptor
import org.antmobile.rijksmuseum.data.remote.network.ForceCacheInterceptor
import org.antmobile.rijksmuseum.data.repositories.ArtsRepositoryImpl
import org.antmobile.rijksmuseum.data.repositories.datasource.ArtsRemoteDataSource
import org.antmobile.rijksmuseum.domain.repositories.ArtsRepository
import org.antmobile.rijksmuseum.domain.usecases.GetArtDetailsByIdUseCase
import org.antmobile.rijksmuseum.domain.usecases.GetListOfArtsUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val appModule = module {

    viewModel { ArtListViewModel(get(), get(), get()) }

    viewModel { ArtDetailViewModel(get(), get()) }

    factory<ErrorConverter> { ErrorConverterImpl(get()) }

    factory { ArtListUiItemMerger(Dispatchers.IO) }
}

val domainModule = module {
    factory { GetListOfArtsUseCase(get(), Dispatchers.IO) }
    factory { GetArtDetailsByIdUseCase(get(), Dispatchers.IO) }
}

val dataModule = module {

    factory { LanguageProvider(get()) }

    single<ArtsRemoteDataSource> {
        ArtsRemoteDataSourceImpl(
            languageProvider = get(),
            api = get()
        )
    }

    single<ArtsRepository> { ArtsRepositoryImpl(get()) }

    single {
        val appContext: Context = get()
        Cache(
            directory = File(appContext.cacheDir, "http_cache"),
            maxSize = 50L * 1024L * 1024L
        )
    }

    single {
        OkHttpClient.Builder()
            .cache(get())
            .addInterceptor(AuthInterceptor("2oWFayp7"))
            .apply {
                if (BuildConfig.DEBUG) {
                    // Will be removed by proguard.
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .addNetworkInterceptor(ForceCacheInterceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(RijksMuseumApi.ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(RijksMuseumApi::class.java)
    }
}
