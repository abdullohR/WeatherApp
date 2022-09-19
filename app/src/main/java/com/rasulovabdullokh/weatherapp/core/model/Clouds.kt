package com.rasulovabdullokh.weatherapp.core.model


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)