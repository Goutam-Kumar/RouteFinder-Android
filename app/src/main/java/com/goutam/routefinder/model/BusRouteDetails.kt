package com.goutam.routefinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusRouteDetails(
    val routeDescription: String? = null,
    val routeName: String? = null,
    val routeNumber: String? = null
): Parcelable
