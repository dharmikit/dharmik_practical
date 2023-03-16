package com.example.fitpeopractical.network

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService){

    suspend fun getPhotos() = flow { emit(apiService.getPhotos()) }

}