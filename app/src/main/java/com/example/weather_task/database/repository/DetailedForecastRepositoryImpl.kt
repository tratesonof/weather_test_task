package com.example.weather_task.database.repository

import com.example.weather_task.database.dao.DetailedForecastDao
import com.example.weather_task.database.entity.detailed_forecast.DetailedForecastEntity

class DetailedForecastRepositoryImpl(private val detailedForecastDao: DetailedForecastDao) : DetailedForecastRepository {

    override suspend fun getDetailedForecast(): Result<List<LocalCityDetailedForecastModel>> =
        Result.success(detailedForecastDao.getAll().map { LocalCityDetailedForecastModel(datetime = it.datetime, temp = it.temp, city = it.city) })

    override suspend fun clearDetailedForecast() {
        detailedForecastDao.deleteAll()

    }

    override suspend fun putDetailedForecast(forecasts: List<LocalCityDetailedForecastModel>) {
        detailedForecastDao.insertAll(detailedForecast = forecasts.map { DetailedForecastEntity(datetime = it.datetime, temp = it.temp, city = it.city, key = it.city + it.datetime) })
    }
}
