package org.antmobile.rijksmuseum.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class ForceCacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .header("Cache-Control", "max-age=$CACHE_AGE")
            .build()
    }

    private companion object {
        val CACHE_AGE = TimeUnit.MINUTES.toSeconds(10)
    }

}
