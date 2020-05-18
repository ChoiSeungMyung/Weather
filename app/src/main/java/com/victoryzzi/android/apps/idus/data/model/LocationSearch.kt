package com.victoryzzi.android.apps.idus.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationSearch(
    val title: String,
    val woeid: String
)