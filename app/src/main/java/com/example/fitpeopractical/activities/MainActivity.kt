package com.example.fitpeopractical.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitpeopractical.R
import com.example.fitpeopractical.adapter.PhotoListAdapter
import com.example.fitpeopractical.databinding.ActivityMainBinding
import com.example.fitpeopractical.models.PhotoListItem
import com.example.fitpeopractical.utils.NetworkState
import com.example.fitpeopractical.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        setUpObservers()
    }

    private fun setUpObservers(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.photoList.collect{
                    when(it){
                        is NetworkState.Loading -> {
                            mainBinding.progressBar.visibility = View.VISIBLE
                        }
                        is NetworkState.Success -> {
                            displayPhotoList(it.data)
                        }
                        is NetworkState.Error -> {
                            noDataDisplay()
                        }
                    }
                }
            }
        }

    }

    private fun displayPhotoList(photoList:List<PhotoListItem>){
        mainBinding.progressBar.visibility = View.GONE
        if(photoList.isEmpty()){
            mainBinding.rvPhotoList.visibility = View.GONE
            mainBinding.tvNoDataFound.visibility = View.VISIBLE
        }else{
            mainBinding.tvNoDataFound.visibility = View.GONE
            mainBinding.rvPhotoList.visibility = View.VISIBLE
        }

        val layoutManager = LinearLayoutManager(this@MainActivity)
        val dividerItemDecoration =
            DividerItemDecoration(mainBinding.rvPhotoList.context, layoutManager.orientation)
        val photoListAdapter = PhotoListAdapter(photoList)
        mainBinding.rvPhotoList.layoutManager = layoutManager
        mainBinding.rvPhotoList.addItemDecoration(dividerItemDecoration)
        mainBinding.rvPhotoList.adapter = photoListAdapter
    }

    private fun noDataDisplay(){
        mainBinding.progressBar.visibility = View.GONE
        mainBinding.rvPhotoList.visibility = View.GONE
        mainBinding.tvNoDataFound.visibility = View.VISIBLE
    }
}