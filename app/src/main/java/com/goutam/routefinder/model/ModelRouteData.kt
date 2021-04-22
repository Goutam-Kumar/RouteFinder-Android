package com.goutam.routefinder.model

data class ModelRouteData(
    val routeTitle: String? = null,
    val routes: List<Route>? = null,
    val totalDistance: Double = 0.0,
    val totalDuration: String? = null,
    val totalFare: Int = 0
)
