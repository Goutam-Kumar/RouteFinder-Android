package com.goutam.routefinder.roomhelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.goutam.routefinder.roomhelper.dao.BusRouteDao
import com.goutam.routefinder.roomhelper.dao.LegDao
import com.goutam.routefinder.roomhelper.dao.RouteDao
import com.goutam.routefinder.roomhelper.dao.TrailDao
import com.goutam.routefinder.roomhelper.tables.TabBusRoute
import com.goutam.routefinder.roomhelper.tables.TabLeg
import com.goutam.routefinder.roomhelper.tables.TabRoute
import com.goutam.routefinder.roomhelper.tables.TabTrails

@Database(
    entities = [TabRoute::class, TabLeg::class, TabTrails::class, TabBusRoute:: class],
    version = 1,
    exportSchema = false
)
abstract class RouteFinderDatabase: RoomDatabase() {
    abstract fun routeDao(): RouteDao
    abstract fun legDao(): LegDao
    abstract fun trailDao(): TrailDao
    abstract fun busRouteDao(): BusRouteDao

    companion object {
        @Volatile private var instance: RouteFinderDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            RouteFinderDatabase::class.java,
            "route_finder_database.db").build()
    }
}
