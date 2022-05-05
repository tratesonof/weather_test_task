package com.example.weather_task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather_task.database.dao.DetailedForecastDao
import com.example.weather_task.database.dao.ForecastDao
import com.example.weather_task.database.entity.detailed_forecast.DetailedForecastEntity
import com.example.weather_task.database.entity.forecast.ForecastEntity

@Database(
    entities =
    [
        ForecastEntity::class,
        DetailedForecastEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "weather_forecast_database"
    }

    abstract fun forecastDao(): ForecastDao

    abstract fun detailedForecastDao(): DetailedForecastDao
}
