package com.rasulovabdullokh.weatherapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasulovabdullokh.weatherapp.core.helpers.Resource
import com.rasulovabdullokh.weatherapp.core.helpers.Resource.Status.ERROR
import com.rasulovabdullokh.weatherapp.core.helpers.Resource.Status.SUCCESS
import com.rasulovabdullokh.weatherapp.core.model.WeatherResponse
import com.rasulovabdullokh.weatherapp.core.repo.MainRepository
import kotlinx.coroutines.launch

class MainVM : ViewModel() {

    private val repo = MainRepository()
    var weatherLD: MutableLiveData<WeatherResponse?> = MutableLiveData<WeatherResponse?>()
    var errorWeatherLD = MutableLiveData<Int?>()
    fun getData(lon: String, lat: String) {
        viewModelScope.launch {
            val response = repo.getData(lat, lon)
            when (response.status) {
                ERROR -> {
                    errorWeatherLD.postValue(response.code)
                }
                SUCCESS -> {
                    weatherLD.postValue(response.data)
                }
            }
        }
    }
}