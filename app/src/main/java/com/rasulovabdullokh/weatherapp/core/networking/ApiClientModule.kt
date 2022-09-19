package com.rasulovabdullokh.weatherapp.core.networking

import androidx.viewbinding.BuildConfig
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rasulovabdullokh.weatherapp.core.app.App
import com.rasulovabdullokh.weatherapp.core.networking.sevice.MainService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClientModule {

    private fun getApiClient(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(getOkHttpClient())
            /*.addCallAdapterFactory(RxJava3CallAdapterFactory.create())*/
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build()
    }

    fun getMain(): MainService {
        return getApiClient().create(MainService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {

        return OkHttpClient()
            .newBuilder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(getChuckerInterception())
            .addInterceptor(interception())
            /*.addInterceptor(interceptor(App.instance))*/
            .build()

    }

    private fun getChuckerCollector(): ChuckerCollector {

        return ChuckerCollector(
            context = App.instance!!,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.FOREVER
        )

    }

    private fun getChuckerInterception(): ChuckerInterceptor {

        return ChuckerInterceptor.Builder(App.instance!!)
            .collector(getChuckerCollector())
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    private fun interception(): Interceptor {

        return Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val builder: Request.Builder = request.newBuilder()
            builder
                .header("Connection", "close")
                .addHeader("Content-type", "application/json")
            /*.addHeader("Authorization", "Token ${AppCache.getCache().token}")*/
            val response = chain.proceed(builder.build())

            response
        }

    }

    private fun getGson(): Gson = GsonBuilder().setLenient().create()


}