package com.example.weather_task.database.repository

interface DetailedForecastRepository {

    suspend fun getDetailedForecast(): Result<List<LocalCityDetailedForecastModel>>

    suspend fun clearDetailedForecast()

    suspend fun putDetailedForecast(forecasts: List<LocalCityDetailedForecastModel>)
}

data class LocalCityDetailedForecastModel(
    val city: String,
    val datetime: String,
    val temp: String
)
