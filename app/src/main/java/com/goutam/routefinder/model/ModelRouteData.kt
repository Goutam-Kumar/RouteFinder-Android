package com.goutam.routefinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelRouteData(
    val routeTitle: String? = null,
    val routes: List<Route>? = null,
    val totalDistance: Double = 0.0,
    val totalDuration: String? = null,
    val totalFare: Int = 0
): Parcelable
