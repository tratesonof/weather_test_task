package com.example.weather_task.feature.main_screen.data.api

import com.example.weather_task.feature.main_screen.data.api.model.RemoteCityForecastModel
import com.example.weather_task.feature.main_screen.domain.model.CityForecastModel

fun RemoteCityForecastModel.toDomain(): CityForecastModel =
    CityForecastModel(city = this.name, temp = String.format("%.0f", this.main.temp))
