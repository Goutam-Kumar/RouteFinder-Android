package com.goutam.routefinder.roomhelper.repository

import com.goutam.routefinder.roomhelper.dao.BusRouteDao
import com.goutam.routefinder.roomhelper.dao.LegDao
import com.goutam.routefinder.roomhelper.dao.RouteDao
import com.goutam.routefinder.roomhelper.dao.TrailDao
import com.goutam.routefinder.roomhelper.tables.TabBusRoute
import com.goutam.routefinder.roomhelper.tables.TabLeg
import com.goutam.routefinder.roomhelper.tables.TabRoute
import com.goutam.routefinder.roomhelper.tables.TabTrails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RouteRepository(
    private val routeDao: RouteDao,
    private val legDao: LegDao,
    private val trailDao: TrailDao,
    private val busRouteDao: BusRouteDao
) {

    suspend fun insertRoute(route: TabRoute): Result<Long> {
        return withContext(Dispatchers.IO){
            val rowId = routeDao.insertRoute(route)
            if (rowId > 0){
                Result.Success(rowId)
            }else{
                Result.Failure(-1)
            }
        }
    }

    suspend fun insertLeg(leg: TabLeg): Result<Long>{
        return withContext(Dispatchers.IO){
            val rowId = legDao.insertLeg(leg)
            if (rowId > 0){
                Result.Success(rowId)
            }else{
                Result.Failure(-1)
            }
        }
    }

    suspend fun insertTrail(trail: TabTrails) {
        withContext(Dispatchers.IO){ trailDao.insertTrail(trail) }
    }

    suspend fun insertBusRoute(busRoute: TabBusRoute){
        withContext(Dispatchers.IO){ busRouteDao.insertBusRoute(busRoute) }
    }

    suspend fun getAllLegs(routeId: Long): List<TabLeg>{
        return withContext(Dispatchers.IO){ legDao.getAllLegs(routeId) }
    }

    suspend fun getAllRoutes(): List<TabRoute>{
        return withContext(Dispatchers.IO){ routeDao.getAllRoutes() }
    }

    suspend fun getAllTrails(legId: Long): List<TabTrails>{
        return withContext(Dispatchers.IO){trailDao.getAllTrails(legId)}
    }

    suspend fun getAllBusRoutes(legId: Long): List<TabBusRoute>{
        return withContext(Dispatchers.IO){busRouteDao.getAllBusRoute(legId)}
    }
}
