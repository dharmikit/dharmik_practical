package com.example.fitpeopractical.network

import com.example.fitpeopractical.models.PhotoListItem
import retrofit2.http.*


interface ApiService {

    @GET("photos")
    suspend fun getPhotos(): List<PhotoListItem>
}