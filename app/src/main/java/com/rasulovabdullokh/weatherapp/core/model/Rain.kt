package com.rasulovabdullokh.weatherapp.core.model


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: Double
)