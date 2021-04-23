package com.goutam.routefinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Trails(
    val distance: Double = 0.0,
    val fareStage: String? = null,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val name: String? = null,
    val seq: Int = 0,
    val time: String? = null
): Parcelable
