package com.rasulovabdullokh.weatherapp.core.repo

import com.rasulovabdullokh.weatherapp.core.model.WeatherResponse
import com.rasulovabdullokh.weatherapp.core.networking.ApiClientModule
import com.rasulovabdullokh.weatherapp.core.helpers.Resource
import com.rasulovabdullokh.weatherapp.core.helpers.getResult

class MainRepository {
    private val service = ApiClientModule.getMain()
    suspend fun getData(lon: String, lat: String) : Resource<WeatherResponse>{
        return getResult {
            service.getWeatherData(lat, lon,"192e64df49df944652349f9a9bf8329a","metric")
        }
    }
}