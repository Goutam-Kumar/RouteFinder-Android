package com.goutam.routefinder.di.daggermodule

import android.content.Context
import com.goutam.routefinder.application.RouteFinderApplication
import com.goutam.routefinder.roomhelper.dao.RouteDao
import com.goutam.routefinder.roomhelper.repository.RouteRepository
import com.goutam.routefinder.utils.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDao(): RouteDao = RouteFinderApplication.database.routeDao()

    @Singleton
    @Provides
    fun provideRouteRepository(): RouteRepository = RouteRepository()

    @Singleton
    @Provides
    fun provideAppContext(): Context = RouteFinderApplication.instance

    @Singleton
    @Provides
    fun provideUtils(): Utils = Utils()
}


