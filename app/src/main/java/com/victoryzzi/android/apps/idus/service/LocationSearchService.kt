package com.victoryzzi.android.apps.idus.service

import com.victoryzzi.android.apps.idus.data.LOCATION_SEARCH_QUERY
import com.victoryzzi.android.apps.idus.data.LOCATION_SEARCH_URL
import com.victoryzzi.android.apps.idus.data.model.LocationSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * LocationSearch API 정보 호출을 위한
 * Retrofit 서비스 인터페이스
 *
 * @param LOCATION_SEARCH_URL = "/api/location/search/"
 * @param LOCATION_SEARCH_QUERY = "query"
 */
interface LocationSearchService {
    @GET(LOCATION_SEARCH_URL)
    fun loadLocationSearch(
        @Query(LOCATION_SEARCH_QUERY)
        query: String
    ): Call<List<LocationSearch>>
}