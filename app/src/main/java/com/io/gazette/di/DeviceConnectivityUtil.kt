package com.io.gazette.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class DeviceConnectivityUtil(private val context: Context) {


    fun hasInternetConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

}