package com.kotlin.allapprxjava.service

import com.example.kotlintest.utils.Constants.Companion.BASE_URL
import com.example.kotlintest.utils.Constants.Companion.DEBUG
import com.example.kotlintest.utils.Constants.Companion.REQUEST_TIMEOUT_DURATION
import com.google.gson.GsonBuilder
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {
    val instances: APIService = Retrofit.Builder().run {
        val gsonBuilder = GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create()

        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create(gsonBuilder))
        client(createRequestInterceptorClient())
            .build()
    }.create(APIService::class.java)

    private fun createRequestInterceptorClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            val request = requestBuilder.cacheControl(CacheControl.FORCE_NETWORK).build()
            chain.proceed(request)
        }
        return if (DEBUG) {
            OkHttpClient.Builder()
                .cache(null)//clear cache
                .addInterceptor(interceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .cache(null)
                .addInterceptor(interceptor)
                .connectTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT_DURATION.toLong(), TimeUnit.SECONDS)
                .build()
        }
    }
}