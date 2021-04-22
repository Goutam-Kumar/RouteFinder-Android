package com.goutam.routefinder.roomhelper.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goutam.routefinder.roomhelper.tables.TabTrails

@Dao
interface TrailDao {
    @Query("SELECT * FROM tab_trails where legId = :legId")
    fun getAllTrails(legId: Long): List<TabTrails>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTrail( route: TabTrails)
}
