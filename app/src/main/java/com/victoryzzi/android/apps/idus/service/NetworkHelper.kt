package com.victoryzzi.android.apps.idus.service

import com.squareup.moshi.Moshi
import com.victoryzzi.android.apps.idus.data.BASE_URL
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
object NetworkHelper {
    private val okhttp by lazy {
        OkHttpClient.Builder().build()
    }

    private val moshi by lazy {
        Moshi.Builder()
            .build()
    }

    // BASE_URL = "https://www.metaweather.com"
    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okhttp)
            .build()
    }

    val locationSearchService: LocationSearchService by lazy {
        retrofit.create(
            LocationSearchService::class.java)
    }

    val locationService: LocationService by lazy {
        retrofit.create(
            LocationService::class.java
        )
    }
}