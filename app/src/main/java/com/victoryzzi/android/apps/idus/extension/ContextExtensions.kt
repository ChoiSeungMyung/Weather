package com.victoryzzi.android.apps.idus.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

/**
 * 네트워크 상태 체크를 위한 확장함수
 */
fun Context.networkCheck(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkAvailability =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (networkAvailability != null
            && networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            && networkAvailability.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        ) {
            return networkAvailability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkAvailability.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
        }
    } else {
        return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
    }

    return false
}

fun Context.makeToast(
    msg: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, msg, duration).show()
}