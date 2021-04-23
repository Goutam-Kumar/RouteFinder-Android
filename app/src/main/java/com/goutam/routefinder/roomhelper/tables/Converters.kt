package com.goutam.routefinder.roomhelper.tables

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.goutam.routefinder.model.Route

class Converters {
val gson = Gson()

    @TypeConverter
    fun fromRouteListToJson(value: List<Route>?): String? {
        value?.let {
            val type = object : TypeToken<List<Route>>() {}.type
            return gson.toJson(value, type)
        }
        return null
    }

    @TypeConverter
    fun toRouteListFromJson(value: String?): List<Route>? {
        value?.let {
            val type = object : TypeToken<List<Route>>() {}.type
            return gson.fromJson(value, type)
        }
        return null
    }
}
