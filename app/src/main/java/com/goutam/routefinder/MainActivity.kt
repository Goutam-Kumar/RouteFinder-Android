package com.goutam.routefinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.goutam.routefinder.roomhelper.RouteFinderDatabase

class MainActivity : AppCompatActivity() {

    private val db by lazy {
        RouteFinderDatabase.invoke(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
