package com.goutam.routefinder.roomhelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.goutam.routefinder.roomhelper.dao.RouteDao
import com.goutam.routefinder.roomhelper.tables.Converters
import com.goutam.routefinder.roomhelper.tables.TabRouteDetails

@Database(
    entities = [TabRouteDetails::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RouteFinderDatabase: RoomDatabase() {
    abstract fun routeDao(): RouteDao
    /*abstract fun legDao(): LegDao
    abstract fun trailDao(): TrailDao
    abstract fun busRouteDao(): BusRouteDao*/

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
