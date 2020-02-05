package com.kotlin.allapprxjava.service

import com.kotlin.allapprxjava.model.RequestWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Responsitory {

    fun fetchWeather(onResult: (isSuccess: Boolean, response: RequestWeather?) -> Unit) {
    }

    companion object {
        private var INSTANCE: Responsitory? = null
        fun getInstance() = INSTANCE ?: Responsitory().also {
            INSTANCE = it
        }
    }
}