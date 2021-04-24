package com.goutam.routefinder.di.daggercomponent

import com.goutam.routefinder.di.daggermodule.AppModule
import com.goutam.routefinder.roomhelper.repository.RouteRepository
import com.goutam.routefinder.ui.routedetails.MapUtils
import com.goutam.routefinder.ui.routelist.RouteListAdapter
import com.goutam.routefinder.ui.routelist.RouteListFragment
import com.goutam.routefinder.ui.routelist.RouteListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(repo: RouteRepository)

    fun inject(vm: RouteListViewModel)

    fun inject(mapUtils: MapUtils)

    fun inject(adapter: RouteListAdapter)

    fun inject(routeListFragment: RouteListFragment)
}
