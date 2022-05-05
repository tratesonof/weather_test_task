package com.example.weather_task.feature.main_screen.domain

import com.example.weather_task.base.utils.attempt
import com.example.weather_task.feature.main_screen.data.api.ForecastRepo

class ForecastInteractor(private val repo: ForecastRepo) {
    suspend fun getCityForecast(city: String) = attempt {
        repo.getCityForecast(city)
    }
}
