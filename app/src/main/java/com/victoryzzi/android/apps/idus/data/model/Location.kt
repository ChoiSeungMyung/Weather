package com.victoryzzi.android.apps.idus.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConsolidatedWeather (
    @Json(name = "consolidated_weather")
    val consolidatedWeather: List<Location>
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "weather_state_name")
    val weatherStateName: String,
    @Json(name = "weather_state_abbr")
    val weatherStateAbbr: String,
    @Json(name = "the_temp")
    val theTemp: Float,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "applicable_date")
    val applicableDate: String
)