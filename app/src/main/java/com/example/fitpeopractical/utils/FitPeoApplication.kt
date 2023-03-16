package com.example.fitpeopractical.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class FitPeoApplication:Application(){

    override fun onCreate() {
        super.onCreate()

    }

}