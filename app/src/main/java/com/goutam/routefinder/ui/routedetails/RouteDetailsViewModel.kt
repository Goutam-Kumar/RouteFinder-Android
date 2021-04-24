package com.goutam.routefinder.ui.routedetails

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.goutam.routefinder.model.Route
import com.goutam.routefinder.model.Trails

class RouteDetailsViewModel: ViewModel() {
    fun getAllLatLng(routeList: List<Route>) = routeList.mapIndexed { index, route ->
        if(index == routeList.lastIndex)
            LatLng(route.destinationLat, route.destinationLong)
        else
            LatLng(route.sourceLat, route.sourceLong)
    }

    fun getPolyLineData(routeList: List<Route>): List<Pair<String, List<LatLng>>> {
        val lisOfLegs = mutableListOf<Pair<String, List<LatLng>>>()
        routeList.forEach {
            when(it.medium){
                BUS -> lisOfLegs.add(BUS to processTrails(it.trails))
                WALK -> lisOfLegs.add(WALK to processRoute(it))
                else -> lisOfLegs.add(TRAIN to processRoute(it))
            }
        }
        return lisOfLegs
    }

    private fun processRoute(it: Route): List<LatLng> {
        val walkLatLng = mutableListOf<LatLng>()
        walkLatLng.add(LatLng(it.sourceLat, it.sourceLong))
        walkLatLng.add(LatLng(it.destinationLat, it.destinationLong))
        return walkLatLng
    }

    private fun processTrails(trails: List<Trails>?): List<LatLng> {
        return trails?.map { LatLng(it.latitude, it.longitude) }.orEmpty()
    }

    companion object{
        const val BUS = "Bus"
        const val WALK = "Walk"
        const val TRAIN = "Train"
    }
}
