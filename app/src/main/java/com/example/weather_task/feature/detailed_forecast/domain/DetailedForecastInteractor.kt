package com.example.weather_task.feature.detailed_forecast.domain

import com.example.weather_task.base.utils.attempt
import com.example.weather_task.feature.detailed_forecast.data.api.DetailedForecastRepo

class DetailedForecastInteractor(private val repo: DetailedForecastRepo) {

    suspend fun getDetailedCityForecast(city: String) = attempt {
        repo.getDetailedCityForecast(city)
    }
}
