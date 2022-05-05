package com.example.weather_task

import android.app.Application
import com.example.weather_task.database.di.databaseModule
import com.example.weather_task.di.appModule
import com.example.weather_task.feature.detailed_forecast.di.detailedForecastModule
import com.example.weather_task.feature.main_screen.di.mainScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, mainScreenModule, detailedForecastModule, databaseModule)
        }
    }
}
