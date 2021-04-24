package com.goutam.routefinder.ui.routelist

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goutam.routefinder.di.daggercomponent.DaggerAppComponent
import com.goutam.routefinder.model.ModelRouteData
import com.goutam.routefinder.roomhelper.repository.Result
import com.goutam.routefinder.roomhelper.repository.RouteRepository
import com.goutam.routefinder.roomhelper.tables.TabRouteDetails
import kotlinx.coroutines.launch
import javax.inject.Inject

class RouteListViewModel: ViewModel() {
    @Inject
    lateinit var routeRepo: RouteRepository

    init {
        DaggerAppComponent.create().inject(this)
    }

    private val _sourceAddress = MutableLiveData<String?>()
    val sourceAddress: LiveData<String?>  = _sourceAddress
    private val _destinationAddress = MutableLiveData<String?>()
    val destinationAddress: LiveData<String?>  = _destinationAddress
    private val _allRouteList = MutableLiveData<List<TabRouteDetails>>()
    val allRouteList: LiveData<List<TabRouteDetails>> = _allRouteList
    private val _tableCleared = MutableLiveData<Boolean>(false)
    val tableCleared: LiveData<Boolean> = _tableCleared

    fun processRouteResponse(routeJsonResponse: List<ModelRouteData>) {
        routeJsonResponse.forEach {
            insertRoute(it)
        }
    }

    fun setAddress(isSource: Boolean, address: String?){
        when(isSource){
            true -> _sourceAddress.value = address
            false -> _destinationAddress.value = address
        }
    }

    fun getAllRoutes(){
        //TODO Uncomment this
        if (validateSourceNDestination()){
            invokeAllRoute()
        }
    }

    private fun validateSourceNDestination(): Boolean{
        return !TextUtils.isEmpty(_sourceAddress.value) && !TextUtils.isEmpty(_destinationAddress.value)
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
                is Result.Success -> getAllRoutes()
                is Result.Failure -> Log.d("Route Operation: ", "Route not inserted!")
            }
        }
    }

    private fun invokeAllRoute(){
        viewModelScope.launch {
            _allRouteList.value = routeRepo.getAllRoutes()
        }
    }

    fun refreshAllRoute(){
        if (validateSourceNDestination()){
            viewModelScope.launch {
                when(routeRepo.deleteAllRoutes()){
                    is Result.Success -> _tableCleared.value = true
                    is Result.Failure -> _tableCleared.value = true
                }
            }
        }
    }

}
