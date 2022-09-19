package com.rasulovabdullokh.weatherapp.core.helpers


import com.rasulovabdullokh.weatherapp.core.helpers.Resource.Companion.error
import com.rasulovabdullokh.weatherapp.core.helpers.Resource.Companion.success

import retrofit2.Response

suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {

    try {
        val response = call.invoke()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return success(body)
            }
        }

        return error("Network call error", response.code())

    } catch (e: Exception) {

        return error(e)

    }

}