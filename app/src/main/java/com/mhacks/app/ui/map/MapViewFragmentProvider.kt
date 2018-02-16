package com.mhacks.app.ui.events

import com.mhacks.app.ui.events.view.EventsFragment
import com.mhacks.app.ui.map.view.MapViewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Provides dependencies into special blend fragment.
 */
@Module
abstract class MapViewFragmentProvider {

    @ContributesAndroidInjector(modules = [MapViewFragmentModule::class])
    abstract fun provideMapViewFragment(): MapViewFragment
}