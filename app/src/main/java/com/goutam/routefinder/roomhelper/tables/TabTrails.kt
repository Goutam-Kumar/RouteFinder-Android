package com.goutam.routefinder.roomhelper.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tab_trails",
    foreignKeys = [ForeignKey(entity = TabLeg::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("legId"),
        onDelete = ForeignKey.CASCADE
    )])
@Parcelize
data class TabTrails(
    @ColumnInfo
    @PrimaryKey(autoGenerate=true)
    val id: Long = 0,
    @ColumnInfo
    val distance: Double = 0.0,
    @ColumnInfo
    val fareStage: String? = null,
    @ColumnInfo
    val latitude: Double = 0.0,
    @ColumnInfo
    val longitude: Double = 0.0,
    @ColumnInfo
    val name: String? = null,
    @ColumnInfo
    val seq: Int = 0,
    @ColumnInfo
    val time: String? = null,
    @ColumnInfo
    val legId: Long = 0
): Parcelable
