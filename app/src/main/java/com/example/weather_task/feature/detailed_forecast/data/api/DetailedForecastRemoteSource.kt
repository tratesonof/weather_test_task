package com.example.weather_task.feature.detailed_forecast.data.api

import com.example.weather_task.feature.detailed_forecast.data.api.model.RemoteDetailedCityForecastModel

class DetailedForecastRemoteSource(private val api: DetailedForecastApi) {
    suspend fun getDetailedCityForecast(city: String): RemoteDetailedCityForecastModel {
        return api.getDetailedCityForecast(city)
    }
}
