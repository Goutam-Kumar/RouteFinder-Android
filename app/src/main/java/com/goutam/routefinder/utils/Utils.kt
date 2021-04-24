package com.goutam.routefinder.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.goutam.routefinder.model.ModelRouteData

@Suppress("UNCHECKED_CAST")
class Utils {

    fun getRouteData(context: Context): List<ModelRouteData> {
        val input = context.assets.open("routes.json").bufferedReader().use {it.readText()}
        return Gson().fromJson<List<ModelRouteData>>(input, object : TypeToken<List<ModelRouteData>>() {}.type)
    }

    fun getTimeInMin(time: String?): Double {
        time?.let { val units = time.split(":".toRegex()).toTypedArray()
            val hours = units[0].toInt()
            val minutes = units[1].toInt()
            val seconds = units[2].toInt()
            val totalSec =  3600 * hours + 60 * minutes + seconds
            return totalSec.toDouble() / 60
        }
        return 0.0
    }
}
