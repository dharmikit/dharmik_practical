package com.example.fitpeopractical.activities

import androidx.appcompat.app.AppCompatActivity
import com.example.fitpeopractical.utils.AppUtils
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(){

    @Inject
    lateinit var appUtils: AppUtils
}