package com.victoryzzi.android.apps.idus.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.victoryzzi.android.apps.idus.R
import com.victoryzzi.android.apps.idus.adapter.WeatherListAdapter
import com.victoryzzi.android.apps.idus.data.model.ConsolidatedWeather
import com.victoryzzi.android.apps.idus.data.model.LocationSearch
import com.victoryzzi.android.apps.idus.data.model.LocationWeather
import com.victoryzzi.android.apps.idus.data.model.WeatherItem
import com.victoryzzi.android.apps.idus.extension.asCallbackFlow
import com.victoryzzi.android.apps.idus.extension.makeToast
import com.victoryzzi.android.apps.idus.service.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ExperimentalCoroutinesApi
class MainViewModel(application: Application) : AndroidViewModel(application) {
    val weatherListAdapter = WeatherListAdapter()

    private val context = getApplication<Application>().baseContext
    private val requestLocationSearchLiveData = MutableLiveData<String>()

    private val locationWeatherList = mutableListOf<WeatherItem>()
    private val requestLocationLiveData = MutableLiveData<List<WeatherItem>>()

    fun clearData() = locationWeatherList.clear()

    //    Coroutine Flow를 이용해서 도시 리스트 받아오기
    val responseLocationSearchLiveData: LiveData<List<LocationSearch>> =
        requestLocationSearchLiveData.switchMap { query ->
            liveData(Dispatchers.IO) {
                NetworkHelper.locationSearchService.loadLocationSearch(query).asCallbackFlow()
                    .catch { e ->
                        context.makeToast(
                            context.getString(R.string.toast_check_connectivity),
                            Toast.LENGTH_LONG
                        )
                        e.printStackTrace()
                    }.collect {
                        emit(it)
                    }
            }
        }

    //    locationWeatherList에 변화가 생기면 responseLiveDataItem에게 변화를 알려줌
    val responseLiveDataItem: LiveData<List<WeatherItem>> =
        requestLocationLiveData.map {
            locationWeatherList
        }

    fun requestLocationSearch(query: String = "se") = requestLocationSearchLiveData.postValue(query)

    //    TODO : requestLocation()을 Repository로 분리
    //    locationList를 받으면 해당 Location의 상세 정보 받아오기
    fun requestLocation(locationList: List<LocationSearch>) {
        locationList.forEach { locationSearch ->
            CoroutineScope(Dispatchers.IO).launch {
                NetworkHelper.locationService.loadLocation(locationSearch.woeid)
                    .enqueue(
                        object : Callback<ConsolidatedWeather> {
                            override fun onResponse(
                                call: Call<ConsolidatedWeather>,
                                response: Response<ConsolidatedWeather>
                            ) {
                                val weatherList = mutableListOf<LocationWeather>()
                                response.body()?.consolidatedWeather?.take(2)?.forEach {
                                    val locationWeather = LocationWeather(
                                        it.weatherStateAbbr,
                                        it.weatherStateName,
                                        it.theTemp,
                                        it.humidity,
                                        it.applicableDate
                                    )
                                    weatherList.add(locationWeather)
                                }

                                val weatherItem = WeatherItem(
                                    locationSearch.title,
                                    weatherList
                                )
                                locationWeatherList.add(weatherItem)
                                requestLocationLiveData.postValue(locationWeatherList)
                            }

                            override fun onFailure(
                                call: Call<ConsolidatedWeather>,
                                t: Throwable
                            ) {
                            }
                        }
                    )
            }
        }
    }
}