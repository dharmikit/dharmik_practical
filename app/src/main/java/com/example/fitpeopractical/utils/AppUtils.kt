package com.example.fitpeopractical.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUtils @Inject constructor(){

    fun isNetworkAvailable(context: Context): Boolean {
        val isConnected: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        isConnected = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return isConnected
    }
}