package org.antmobile.rijksmuseum.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val urlWithApiKey = request.url.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(urlWithApiKey)
                .build()
        )
    }
}
