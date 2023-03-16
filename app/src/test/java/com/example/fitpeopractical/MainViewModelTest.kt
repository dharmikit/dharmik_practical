package com.example.fitpeopractical

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fitpeopractical.network.ApiRepository
import com.example.fitpeopractical.network.ApiService
import com.example.fitpeopractical.viewmodels.MainViewModel
import com.google.gson.Gson
import io.mockk.clearAllMocks
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class MainViewModelTest {

    private val timeUnit:Long = 10
    private val photoList = "photo_list.json"

    private lateinit var apiRepository: ApiRepository
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    private fun getResponseFile(file: String): String {
        return javaClass.classLoader?.getResourceAsStream(file)!!.bufferedReader()
            .use { it.readText() }.trim()
    }

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiRepository = mockk()
        mainViewModel = MainViewModel(apiRepository)

        val client = OkHttpClient.Builder()
            .connectTimeout(timeUnit, TimeUnit.SECONDS)
            .readTimeout(timeUnit, TimeUnit.SECONDS)
            .writeTimeout(timeUnit, TimeUnit.SECONDS)
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun photoListSuccess() {
        mockWebServer.enqueueResponse(photoList, HttpURLConnection.HTTP_OK)
        runBlocking {
            val result = getResponseFile(photoList)
            val response = Gson().toJson(apiService.getPhotos())
            assertEquals(result, response)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        clearAllMocks()
    }
}