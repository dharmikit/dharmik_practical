package com.example.fitpeopractical.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitpeopractical.models.PhotoListItem
import com.example.fitpeopractical.network.ApiRepository
import com.example.fitpeopractical.utils.AppUtils
import com.example.fitpeopractical.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val apiRepository: ApiRepository,
        appUtils: AppUtils, context: Context): ViewModel(){

    private val _photoList = MutableStateFlow<NetworkState<List<PhotoListItem>>>(NetworkState.Loading)
    val photoList: StateFlow<NetworkState<List<PhotoListItem>>> = _photoList

    init {
        if(appUtils.isNetworkAvailable(context)) {
            fetchPhotoList()
        }
    }

    private fun fetchPhotoList() {
            viewModelScope.launch(Dispatchers.Main) {
                    _photoList.value = NetworkState.Loading
                    apiRepository.getPhotos()
                        .flowOn(Dispatchers.IO)
                        .catch { e ->
                            _photoList.value = NetworkState.Error(e.toString())
                        }
                        .collect {
                            _photoList.value = NetworkState.Success(it)
                        }
            }
    }
}