package com.rasulovabdullokh.weatherapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.rasulovabdullokh.weatherapp.core.base.BaseActivity
import com.rasulovabdullokh.weatherapp.databinding.ActivitySplashBinding
import com.rasulovabdullokh.weatherapp.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    override fun getView(): View? {
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        setHandler()
    }

    private fun setHandler() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 1000)

    }

}