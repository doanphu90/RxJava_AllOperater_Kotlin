package com.kotlin.allapprxjava.service

import com.kotlin.allapprxjava.model.RequestWeather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("current?access_key=53ba34723e85c0269086f8587407f3f4")
    fun fetchInfomation(@Query("query") city: String):Single<RequestWeather>
}