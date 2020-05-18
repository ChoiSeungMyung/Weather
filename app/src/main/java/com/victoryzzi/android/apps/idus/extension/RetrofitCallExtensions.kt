package com.victoryzzi.android.apps.idus.extension

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Retrofit을 이용해 네트워크 통신을 한 후
 * 결과값을 Kotlin의 Flow로 받기 위한 확장함수
 */
@ExperimentalCoroutinesApi
fun <T> Call<T>.asCallbackFlow() = callbackFlow<T> {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            when(response.isSuccessful) {
                true -> response.body()?.let {
                    offer(it)
                } ?: close()

                else -> close()
            }
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            close(throwable)
        }
    })

    awaitClose()
}