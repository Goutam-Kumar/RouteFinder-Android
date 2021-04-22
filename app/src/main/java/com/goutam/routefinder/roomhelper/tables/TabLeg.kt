package com.goutam.routefinder.roomhelper.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tab_leg",
    foreignKeys = [ForeignKey(entity = TabRoute::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("routeId"),
        onDelete = CASCADE
    )]
)
@Parcelize
data class TabLeg(
    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    val id: Long = 0,
    @ColumnInfo
    val destinationLat: Double = 0.0,
    @ColumnInfo
    val destinationLong: Double = 0.0,
    @ColumnInfo
    val destinationTime: String? = null,
    @ColumnInfo
    val destinationTitle: String? = null,
    @ColumnInfo
    val distance: Double = 0.0,
    @ColumnInfo
    val duration: String? = null,
    @ColumnInfo
    val fare: Int = 0,
    @ColumnInfo
    val medium: String? = null,
    @ColumnInfo
    val rideEstimation: String? = null,
    @ColumnInfo
    val routeName: String? = null,
    @ColumnInfo
    val sourceLat: Double = 0.0,
    @ColumnInfo
    val sourceLong: Double = 0.0,
    @ColumnInfo
    val sourceTime: String? = null,
    @ColumnInfo
    val sourceTitle: String? = null,
    @ColumnInfo
    val routeId: Long = 0
): Parcelable
