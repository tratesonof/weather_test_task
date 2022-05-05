package com.example.weather_task.database.entity.forecast

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_table")
data class ForecastEntity(
    @PrimaryKey val city: String,
    val temp: String
)
