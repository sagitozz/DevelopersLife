package com.application.developerslife.model.network

import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * @autor d.snytko
 */
private const val TIMEOUT_IN_SECONDS = 50
private const val BASE_URL = "https://developerslife.ru/"

class GifRestApi(private val factory: Converter.Factory) {

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()

    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(factory)
            .build()

    fun getEndPoint(): GifEndpoint {
        return retrofit.create(GifEndpoint::class.java)
    }
}
