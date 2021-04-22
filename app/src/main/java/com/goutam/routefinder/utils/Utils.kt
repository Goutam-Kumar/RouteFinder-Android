package com.goutam.routefinder.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.goutam.routefinder.model.ModelRouteData

@Suppress("UNCHECKED_CAST")
class Utils {
    companion object{

        fun getRouteData(context: Context): List<ModelRouteData> {
            val input = context.assets.open("routes.json").bufferedReader().use {it.readText()}
            return Gson().fromJson<List<ModelRouteData>>(input, object : TypeToken<List<ModelRouteData>>() {}.type)
        }

        inline fun <V: ViewModel> getViewModelFactory(crossinline getViewModelObject: () -> V): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return getViewModelObject() as T
                }
            }
        }
    }
}
