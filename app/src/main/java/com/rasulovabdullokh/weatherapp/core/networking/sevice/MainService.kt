package com.rasulovabdullokh.weatherapp.core.networking.sevice

import com.rasulovabdullokh.weatherapp.core.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {

    @GET("/data/2.5/weather")
    suspend fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String,
        @Query("units") units : String
    ) : Response<WeatherResponse>
}