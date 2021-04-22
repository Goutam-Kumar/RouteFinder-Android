package com.goutam.routefinder.roomhelper.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goutam.routefinder.roomhelper.tables.TabLeg

@Dao
interface LegDao {
    @Query("SELECT * FROM tab_leg where routeId = :routeId")
    fun getAllLegs(routeId: Long): List<TabLeg>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLeg( route: TabLeg): Long
}
