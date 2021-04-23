package com.goutam.routefinder.ui.routelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goutam.routefinder.model.ModelRouteData
import com.goutam.routefinder.roomhelper.dao.RouteDao
import com.goutam.routefinder.roomhelper.repository.Result
import com.goutam.routefinder.roomhelper.repository.RouteRepository
import com.goutam.routefinder.roomhelper.tables.TabRouteDetails
import kotlinx.coroutines.launch

class RouteListViewModel(
        routeDao: RouteDao
): ViewModel() {
    private val routeRepo = RouteRepository(routeDao)

    private val _allRouteList = MutableLiveData<List<TabRouteDetails>>()
    val allRouteList: LiveData<List<TabRouteDetails>> = _allRouteList

    fun processRouteResponse(routeJsonResponse: List<ModelRouteData>) {
        routeJsonResponse.forEach {
            insertRoute(it)
        }
    }

    private fun insertRoute(data: ModelRouteData) {
        val tabRoute = TabRouteDetails(
                routeTitle = data.routeTitle,
                totalDistance = data.totalDistance,
                totalDuration = data.totalDuration,
                totalFare = data.totalFare,
                routes = data.routes
        )
        viewModelScope.launch {
            when(routeRepo.insertRoute(tabRoute)){
                is Result.Success -> invokeAllRoute()
                is Result.Failure -> Log.d("Route Operation: ", "Route not inserted!")
            }
        }
    }

    fun invokeAllRoute(){
        viewModelScope.launch {
            _allRouteList.value = routeRepo.getAllRoutes()
        }
    }

}
