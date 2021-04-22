package com.goutam.routefinder.roomhelper.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tab_route", indices = [Index(value = ["totalDistance","totalDuration"], unique = true)])
@Parcelize
data class TabRoute(
    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    val id: Long = 0,
    @ColumnInfo
    val routeTitle: String? = null,
    @ColumnInfo
    val totalDistance: Double = 0.0,
    @ColumnInfo
    val totalDuration: String? = null,
    @ColumnInfo
    val totalFare: Int = 0
):Parcelable
