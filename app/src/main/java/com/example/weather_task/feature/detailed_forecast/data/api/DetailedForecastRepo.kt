package com.example.weather_task.feature.detailed_forecast.data.api

import com.example.weather_task.feature.detailed_forecast.domain.model.DetailedForecastModel

interface DetailedForecastRepo {
    suspend fun getDetailedCityForecast(city: String): List<DetailedForecastModel>
}
