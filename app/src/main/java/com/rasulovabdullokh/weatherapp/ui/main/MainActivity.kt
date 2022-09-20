package com.rasulovabdullokh.weatherapp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rasulovabdullokh.weatherapp.R
import com.rasulovabdullokh.weatherapp.core.base.BaseActivity
import com.rasulovabdullokh.weatherapp.databinding.ActivityMainBinding
import com.rasulovabdullokh.weatherapp.databinding.ActivitySplashBinding

class MainActivity : BaseActivity() {
    private var lat = ""
    private var lon = ""
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val mainVM: MainVM by viewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun getView(): View? {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getCurrentLocation()
        loadObserv()

    }

    private fun getCurrentLocation() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),100)
            return
        }
        val locc = fusedLocationProviderClient.lastLocation
        locc.addOnSuccessListener {
            if (it!=null){
                lat = it.latitude.toString()
                lon = it.longitude.toString()
                mainVM.getData(lat, lon)
            }else{
                Toast.makeText(this, "Sorry", Toast.LENGTH_SHORT).show()
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {

        fusedLocationProviderClient.lastLocation?.addOnSuccessListener {

            if (it == null) {
                Toast.makeText(this, "Sorry Can't get Location", Toast.LENGTH_SHORT).show()
            } else it.apply {

                val lat = it.latitude
                val lon = it.longitude

                mainVM.getData(lat.toString(), lon.toString())
            }

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getLocation()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun loadObserv() {
        mainVM.weatherLD.observe(this) {
            binding.cityName.text = it?.name
            binding.temp.text = it?.main?.temp?.toInt().toString()
            val cloudType = it?.weather?.get(0)?.main?.toString()

            if (cloudType == "Clear") {
                binding.weather.setBackgroundResource(R.drawable.sun)
            } else if (cloudType == "Clouds") {
                binding.weather.setBackgroundResource(R.drawable.cloud)
            } else if (cloudType == "Rain") {
                binding.weather.setBackgroundResource(R.drawable.rain)
            } else if (cloudType == "Snow") {
                binding.weather.setBackgroundResource(R.drawable.snow)
            } else if (cloudType == "Thunderstorm") {
                binding.weather.setBackgroundResource(R.drawable.storm)
            }

        }
        mainVM.errorWeatherLD.observe(this) {

        }
    }

}