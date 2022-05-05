package com.example.weather_task.feature.main_screen.data.api

import com.example.weather_task.feature.main_screen.domain.model.CityForecastModel

interface ForecastRepo {
    suspend fun getCityForecast(city: String): CityForecastModel
}
