package com.example.weather_task.database.repository

import com.example.weather_task.database.dao.ForecastDao
import com.example.weather_task.database.entity.forecast.ForecastEntity

class ForecastRepositoryImpl(private val forecastDao: ForecastDao) : ForecastRepository {

    override suspend fun getForecasts(): Result<List<LocalCityForecastModel>> =
        Result.success(forecastDao.getAll().map { LocalCityForecastModel(city = it.city, temp = it.temp) })

    override suspend fun clearForecasts() {
        forecastDao.deleteAll()
    }

    override suspend fun putForecast(forecast: LocalCityForecastModel) {
        forecastDao.insert(forecasts = ForecastEntity(city = forecast.city, temp = forecast.temp))
    }
}
