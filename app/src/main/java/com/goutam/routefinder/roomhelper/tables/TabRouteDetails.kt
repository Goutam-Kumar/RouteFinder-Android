package com.goutam.routefinder.roomhelper.tables

import android.os.Parcelable
import androidx.room.*
import com.goutam.routefinder.model.Route
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tab_route", indices = [Index(value = ["totalDistance","totalDuration"], unique = true)])
@Parcelize
data class TabRouteDetails(
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
    val totalFare: Int = 0,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "routes")
    val routes: List<Route>? = null
):Parcelable
