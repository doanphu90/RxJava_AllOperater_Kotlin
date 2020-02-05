package com.kotlin.allapprxjava.model

data class RequestWeather(
    val request:Request?=null,
    val location:LocationNew?=null,
    val current:CurrentData?=null
)

data class Request(
    val type:String? =null,
    val query:String?=null,
    val language:String?=null,
    val unit:String?=null
)

data class LocationNew(
    val name:String?=null,
    val country:String?=null,
    val region:String?=null,
    val lat:String?=null,
    val lon:String?=null,
    val timezoneId:String?=null,
    val localtime:String?=null,
    val localtimeEpoch:Double?=null,
    val utcOffset:String?=null
)

data class CurrentData(
    val observationTime:String?=null,
    val temperature:Double?=null,
    val weather_code:Double?=null,
//    val weather_icons:String?=null,
//    val weather_descriptions:String?=null,
    val wind_speed:Int?=null,
    val wind_degree:Int?=null,
    val wind_dir:String?=null,
    val pressure:Int?=null,
    val precip:Int?=null,
    val humidity:Int?=null,
    val cloudcover:Int?=null,
    val feelslike:Int?=null,
    val uv_index:Int?=null,
    val visibility:Int?=null,
    val is_day:Boolean?=null
)