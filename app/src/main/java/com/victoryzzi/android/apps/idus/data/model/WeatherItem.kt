package com.victoryzzi.android.apps.idus.data.model


data class WeatherItem(
    val locationName: String,
    val weatherList: List<LocationWeather> = emptyList()
)

data class LocationWeather(
    val weatherAbbr: String,
    val weatherStateName: String,
    val weatherTemp: Float,
    val weatherHumidity: Int,
    val weatherDate: String
){
    fun getWeatherTemp() = "${weatherTemp.toInt()}℃"
    fun getWeatherHumidity() = "${weatherHumidity}%"
}