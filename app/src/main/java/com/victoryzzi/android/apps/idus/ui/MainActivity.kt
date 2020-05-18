package com.victoryzzi.android.apps.idus.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.victoryzzi.android.apps.idus.R
import com.victoryzzi.android.apps.idus.data.model.WeatherItem
import com.victoryzzi.android.apps.idus.extension.makeSnackBar
import com.victoryzzi.android.apps.idus.extension.networkCheck
import com.victoryzzi.android.apps.idus.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

//  TODO : 테이블 타이틀 헤더 만들기
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        main_weather_list_refresh.apply {
            isRefreshing = true
            setOnRefreshListener(this@MainActivity)
        }
        main_weather_list_refresh.isRefreshing = true
        main_weather_list_view.adapter = mainViewModel.weatherListAdapter

//        네트워크 체크 후 데이터 요청
        when(networkCheck()) {
            true -> {
                mainViewModel.apply {
                    requestLocationSearch()
                    responseLocationSearchLiveData.observe(this@MainActivity, Observer {
                        mainViewModel.requestLocation(it)
                    })

                    responseLiveDataItem.observe(this@MainActivity, Observer {
                        main_weather_list_refresh.isRefreshing = false
                        weatherListAdapter.submitList(it as MutableList<WeatherItem>?)
                    })
                }
            }

            false -> {
                main_view.makeSnackBar(getString(R.string.toast_check_connectivity), Snackbar.LENGTH_LONG)
            }
        }
    }

    override fun onRefresh() {
        when(networkCheck()) {
            true -> {
                mainViewModel.run {
                    clearData()
                    requestLocationSearch()
                }
            }

            false -> {
                main_view.makeSnackBar(getString(R.string.toast_check_connectivity))
            }
        }
    }
}
