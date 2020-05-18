package com.victoryzzi.android.apps.idus.service

import com.victoryzzi.android.apps.idus.data.LOCATION_PATH
import com.victoryzzi.android.apps.idus.data.LOCATION_URL
import com.victoryzzi.android.apps.idus.data.model.ConsolidatedWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Location API 정보 호출을 위한
 * Retrofit 서비스 인터페이스
 *
 * @param LOCATION_URL = "/api/location/{woeid}/"
 * @param LOCATION_PATH = "woeid"
 */
interface LocationService {
    @GET(LOCATION_URL)
    fun loadLocation(
        @Path(LOCATION_PATH)
        locationId: String
    ): Call<ConsolidatedWeather>
}