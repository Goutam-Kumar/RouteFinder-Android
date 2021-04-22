package com.goutam.routefinder.roomhelper.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goutam.routefinder.roomhelper.tables.TabBusRoute

@Dao
interface BusRouteDao {
    @Query("SELECT * FROM tab_bus_route where legId = :legId")
    fun getAllBusRoute(legId: Long): List<TabBusRoute>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBusRoute( route: TabBusRoute)
}
