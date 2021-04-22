package com.goutam.routefinder.roomhelper.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tab_bus_route",
    foreignKeys = [ForeignKey(entity = TabLeg::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("legId"),
        onDelete = ForeignKey.CASCADE
    )])
@Parcelize
data class TabBusRoute(
    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    val id: Long = 0,
    @ColumnInfo
    val routeDescription: String? = null,
    @ColumnInfo
    val routeName: String? = null,
    @ColumnInfo
    val routeNumber: String? = null,
    @ColumnInfo
    val legId: Long = 0
): Parcelable
