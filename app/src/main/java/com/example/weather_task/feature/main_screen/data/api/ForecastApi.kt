package com.example.weather_task.feature.main_screen.data.api

import com.example.weather_task.constants.Constants
import com.example.weather_task.feature.detailed_forecast.data.api.model.RemoteDetailedCityForecastModel
import com.example.weather_task.feature.main_screen.data.api.model.RemoteCityForecastModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("data/2.5/weather?")
    suspend fun getCityForecast(
        @Query(value = "q") cityName: String = "",
        @Query(value = "appid") apiKey: String = Constants.API_KEY,
        @Query(value = "units") units: String = "metric"
    ): RemoteCityForecastModel
}
