package com.example.weather_task.feature.main_screen.data.api

import com.example.weather_task.feature.main_screen.domain.model.CityForecastModel

class ForecastRepoImpl(private val source: ForecastRemoteSource) : ForecastRepo {
    override suspend fun getCityForecast(city: String): CityForecastModel {
        return source.getCityForecast(city).toDomain()
    }
}
