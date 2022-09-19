package com.rasulovabdullokh.weatherapp.core.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getView()?.let {
            setContentView(it)
        }
        onCreated(savedInstanceState)
    }


    abstract fun getView(): View?
    abstract fun onCreated(savedInstanceState: Bundle?)
}
