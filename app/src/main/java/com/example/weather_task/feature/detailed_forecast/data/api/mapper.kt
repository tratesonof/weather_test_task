package com.example.weather_task.feature.detailed_forecast.data.api

import com.example.weather_task.feature.detailed_forecast.data.api.model.RemoteDetailedCityForecastModel
import com.example.weather_task.feature.detailed_forecast.domain.model.DetailedForecastModel

fun RemoteDetailedCityForecastModel.toDomain(): List<DetailedForecastModel> =
    this.list.map { DetailedForecastModel(temp = String.format("%.0f", it.main.temp), datetime = it.dt_txt) }
