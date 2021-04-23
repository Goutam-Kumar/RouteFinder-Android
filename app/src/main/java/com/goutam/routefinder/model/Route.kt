package com.goutam.routefinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Route(
    val busRouteDetails: BusRouteDetails? = null,
    val destinationLat: Double = 0.0,
    val destinationLong: Double = 0.0,
    val destinationTime: List<String>? = null,
    val destinationTitle: String? = null,
    val distance: Double = 0.0,
    val duration: String? = null,
    val fare: Int = 0,
    val medium: String? = null,
    val rideEstimation: String? = null,
    val routeName: String? = null,
    val sourceLat: Double = 0.0,
    val sourceLong: Double = 0.0,
    val sourceTime: List<String>? = null,
    val sourceTitle: String? = null,
    val trails: List<Trails>? = null
): Parcelable
