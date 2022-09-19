package com.rasulovabdullokh.weatherapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.rasulovabdullokh.weatherapp.R
import com.rasulovabdullokh.weatherapp.core.base.BaseActivity
import com.rasulovabdullokh.weatherapp.databinding.ActivityMainBinding
import com.rasulovabdullokh.weatherapp.databinding.ActivitySplashBinding

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val lat = "41.324715528601615"
    private val lon = "69.32527029999999"
    private val mainVM: MainVM by viewModels()

    override fun getView(): View? {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        mainVM.getData(lat,lon)
        loadObserv()

    }

    private fun loadObserv() {
        mainVM.weatherLD.observe(this){
            binding.cityName.text = it?.name
            binding.temp.text = it?.main?.temp?.toInt().toString()
            val cloudType = it?.weather?.get(0)?.main?.toString()

            if(cloudType == "Clear" ){
                binding.weather.setBackgroundResource(R.drawable.sun)
            }else if(cloudType=="Clouds"){
                binding.weather.setBackgroundResource(R.drawable.cloud)
            }else if(cloudType=="Rain"){
                binding.weather.setBackgroundResource(R.drawable.rain)
            }else if(cloudType=="Snow"){
                binding.weather.setBackgroundResource(R.drawable.snow)
            }else if(cloudType=="Thunderstorm"){
                binding.weather.setBackgroundResource(R.drawable.storm)
            }

        }
        mainVM.errorWeatherLD.observe(this){

        }
    }

}