package com.example.weather_task.feature.detailed_forecast.data.api.model

import com.google.gson.annotations.SerializedName

data class RemoteDetailedCityForecastModel(
    @SerializedName("city")
    val city: City,
    @SerializedName("list")
    val list: List<RemoteListModel>
)

data class City(
    @SerializedName("name")
    val name: String
)

data class RemoteListModel(
    @SerializedName("main")
    val main: RemoteMainModel,
    @SerializedName("dt_txt")
    val dt_txt: String
)

data class RemoteMainModel(
    @SerializedName("temp")
    val temp: Double
)
