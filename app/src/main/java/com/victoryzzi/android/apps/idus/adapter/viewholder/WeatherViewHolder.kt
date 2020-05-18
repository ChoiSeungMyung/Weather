package com.victoryzzi.android.apps.idus.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victoryzzi.android.apps.idus.data.model.WeatherItem
import com.victoryzzi.android.apps.idus.databinding.ListItemWeatherBinding

class WeatherViewHolder(private val binding: ListItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
    val ABBR_URL = "https://www.metaweather.com/static/img/weather/png/64/"
    fun bind(weatherItem: WeatherItem) = binding.apply {
        weatherData = weatherItem

        Glide.with(itemWeatherTodayAbbr)
            .load("$ABBR_URL${weatherItem.weatherList[0].weatherAbbr}.png")
            .into(itemWeatherTodayAbbr)

        Glide.with(itemWeatherTodayAbbr)
            .load("$ABBR_URL${weatherItem.weatherList[1].weatherAbbr}.png")
            .into(itemWeatherTomorrowAbbr)
    }
}
