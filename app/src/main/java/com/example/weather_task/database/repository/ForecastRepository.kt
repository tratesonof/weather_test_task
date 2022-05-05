package com.example.weather_task.database.repository

interface ForecastRepository {

    suspend fun getForecasts(): Result<List<LocalCityForecastModel>>

    suspend fun clearForecasts()

    suspend fun putForecast(forecasts: LocalCityForecastModel)
}

data class LocalCityForecastModel(
    val city: String,
    val temp: String,
)
