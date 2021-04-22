package com.goutam.routefinder.ui.routelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goutam.routefinder.model.BusRouteDetails
import com.goutam.routefinder.model.ModelRouteData
import com.goutam.routefinder.model.Route
import com.goutam.routefinder.model.Trails
import com.goutam.routefinder.roomhelper.dao.BusRouteDao
import com.goutam.routefinder.roomhelper.dao.LegDao
import com.goutam.routefinder.roomhelper.dao.RouteDao
import com.goutam.routefinder.roomhelper.dao.TrailDao
import com.goutam.routefinder.roomhelper.repository.Result
import com.goutam.routefinder.roomhelper.repository.RouteRepository
import com.goutam.routefinder.roomhelper.tables.TabBusRoute
import com.goutam.routefinder.roomhelper.tables.TabLeg
import com.goutam.routefinder.roomhelper.tables.TabRoute
import com.goutam.routefinder.roomhelper.tables.TabTrails
import kotlinx.coroutines.launch

class RouteListViewModel(
     routeDao: RouteDao,
     legDao: LegDao,
     trailDao: TrailDao,
     busRouteDao: BusRouteDao
): ViewModel() {
    private val routeRepo = RouteRepository(routeDao, legDao, trailDao, busRouteDao)

    fun processRouteResponse(routeJsonResponse: List<ModelRouteData>) {
        routeJsonResponse.forEach {
            insertRoute(it)
        }
    }

    private fun insertRoute(data: ModelRouteData) {
        val tabRoute = TabRoute(
            routeTitle = data.routeTitle,
            totalDistance = data.totalDistance,
            totalDuration = data.totalDuration,
            totalFare = data.totalFare
        )
        viewModelScope.launch {
            when(val insertResponse = routeRepo.insertRoute(tabRoute)){
                is Result.Success -> insertLeg(data, insertResponse.rowId)
                is Result.Failure -> Log.d("Route Operation: ", "Route not inserted!")
            }
        }
    }

    private suspend fun insertLeg(data: ModelRouteData, routeId: Long) {
        data.routes?.forEach {
            val tabLeg = it.transformToTabLeg(routeId)
            when(val insertLegResponse = routeRepo.insertLeg(tabLeg)){
                is Result.Success -> {
                    insertTrails(it, insertLegResponse.rowId)
                    insertBusRoute(it, insertLegResponse.rowId)
                }
                is Result.Failure -> Log.d("Leg Operation: ", "Leg not inserted!")
            }
        }
    }

    private fun insertBusRoute(routeData: Route, legId: Long) {
        viewModelScope.launch {
            routeData.busRouteDetails?.let {
                val tabBusRoute = it.transformToTabBusRoute(legId)
                routeRepo.insertBusRoute(tabBusRoute)
            }
        }
    }

    private fun insertTrails(routeData: Route, legId: Long) {
        viewModelScope.launch {
            routeData.trails?.forEach {
                val tabTrails = it.transformToTabTrail(legId)
                routeRepo.insertTrail(tabTrails)
            }
        }
    }

    private fun Route.transformToTabLeg(routeId: Long) = TabLeg(
        destinationLat = destinationLat,
        destinationLong = destinationLong,
        destinationTime = destinationTime?.first(),
        destinationTitle = destinationTitle,
        distance = distance,
        duration = duration,
        fare = fare,
        medium = medium,
        rideEstimation = rideEstimation,
        routeName = routeName,
        sourceLat = sourceLat,
        sourceLong = sourceLong,
        sourceTime = sourceTime?.first(),
        sourceTitle = sourceTitle,
        routeId = routeId
    )

    private fun Trails.transformToTabTrail(legId: Long) = TabTrails(
        distance = distance,
        name = name,
        fareStage = fareStage,
        latitude = latitude,
        longitude = longitude,
        seq = seq,
        time = time,
        legId = legId
    )

    private fun BusRouteDetails.transformToTabBusRoute(legId: Long) = TabBusRoute(
        routeName = routeName,
        routeDescription = routeDescription,
        routeNumber = routeNumber,
        legId = legId
    )

}
