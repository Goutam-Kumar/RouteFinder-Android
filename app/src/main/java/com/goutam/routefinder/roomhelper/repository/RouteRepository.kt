package com.goutam.routefinder.roomhelper.repository

import com.goutam.routefinder.roomhelper.dao.RouteDao
import com.goutam.routefinder.roomhelper.tables.TabRouteDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RouteRepository(
    private val routeDao: RouteDao
) {

    suspend fun insertRoute(route: TabRouteDetails): Result<Long> {
        return withContext(Dispatchers.IO){
            val rowId = routeDao.insertRoute(route)
            if (rowId > 0){
                Result.Success(rowId)
            }else{
                Result.Failure(-1)
            }
        }
    }

    suspend fun getAllRoutes(): List<TabRouteDetails>{
        return withContext(Dispatchers.IO){routeDao.getAllRoutes()}
    }

    suspend fun deleteAllRoutes(): Result<Long>{
        return try {
            withContext(Dispatchers.IO){
                val response = routeDao.deleteAllRoute()
                if (response > 0){
                    Result.Success(1)
                }else{
                    Result.Failure(-1)
                }
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.Failure(-1)
        }
    }
}
