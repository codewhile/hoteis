package com.viniciusmello.fragments

import android.app.Application
import android.provider.Settings
import com.viniciusmello.fragments.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module


class HotelApp :
    Application() {

    override fun onCreate() {

        super.onCreate()

         startKoin {
             androidLogger()
             androidContext(this@HotelApp)
             modules(androidModule)
        }

    }



    override fun onTerminate() {

        super.onTerminate()
        stopKoin()

    }

}