package com.example.weather_task.feature.main_screen.data.api.model

import com.google.gson.annotations.SerializedName

data class RemoteCityForecastModel(
    @SerializedName("main")
    val main: RemoteMainModel,
    @SerializedName("name")
    val name: String
)

data class RemoteMainModel(
    @SerializedName("temp")
    val temp: Double
)
