package com.example.weather_task.feature.detailed_forecast.data.api

import com.example.weather_task.constants.Constants
import com.example.weather_task.feature.detailed_forecast.data.api.model.RemoteDetailedCityForecastModel
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailedForecastApi {

    @GET("data/2.5/forecast?")
    suspend fun getDetailedCityForecast(
        @Query(value = "q") cityName: String = "",
        @Query(value = "appid") apiKey: String = Constants.API_KEY,
        @Query(value = "units") units: String = "metric"
    ): RemoteDetailedCityForecastModel
}
