package com.example.weather_task.feature.detailed_forecast.data.api

import com.example.weather_task.feature.detailed_forecast.domain.model.DetailedForecastModel

class DetailedForecastRepoImpl(private val source: DetailedForecastRemoteSource) : DetailedForecastRepo {

    override suspend fun getDetailedCityForecast(city: String): List<DetailedForecastModel> =
        source.getDetailedCityForecast(city).toDomain()
}
