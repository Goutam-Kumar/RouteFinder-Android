package com.goutam.routefinder.application

import android.app.Application
import com.goutam.routefinder.roomhelper.RouteFinderDatabase

class RouteFinderApplication: Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        database = RouteFinderDatabase.invoke(this)
    }

    companion object {
        lateinit var instance: RouteFinderApplication
        lateinit var database: RouteFinderDatabase
    }
}
