package com.goutam.routefinder.roomhelper.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.goutam.routefinder.roomhelper.tables.TabRoute

@Dao
interface RouteDao {
    @Query("SELECT * FROM tab_route")
    fun getAllRoutes(): List<TabRoute>

    @Insert(onConflict = IGNORE)
    fun insertRoute( route: TabRoute): Long

    @Query("DELETE FROM tab_route")
    fun deleteAllRoute()
}
