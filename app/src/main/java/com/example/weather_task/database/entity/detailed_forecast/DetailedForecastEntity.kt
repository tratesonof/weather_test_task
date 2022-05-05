package com.example.weather_task.database.entity.detailed_forecast

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detailed_forecast_table")
data class DetailedForecastEntity(
    @PrimaryKey val key: String,
    val datetime: String,
    val city: String,
    val temp: String,
)
