package com.example.weather_task.feature.main_screen.data.api

import com.example.weather_task.feature.main_screen.data.api.model.RemoteCityForecastModel
import com.example.weather_task.feature.detailed_forecast.data.api.model.RemoteDetailedCityForecastModel

class ForecastRemoteSource(private val api: ForecastApi) {
    suspend fun getCityForecast(city: String): RemoteCityForecastModel {
        return api.getCityForecast(city)
    }
}
