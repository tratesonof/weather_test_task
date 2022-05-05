package com.example.weather_task.feature.detailed_forecast.ui

import com.example.weather_task.base.utils.Event
import com.example.weather_task.feature.main_screen.domain.model.CityForecastModel
import com.example.weather_task.feature.detailed_forecast.domain.model.DetailedForecastModel

data class ViewState(
    val detailedForecastList: List<DetailedForecastModel>,
    val city: String,
    val isLoading: Boolean
)

sealed class UiEvent : Event {
    object GetDetailedForecast: UiEvent()
}

sealed class DataEvent : Event {
    object LoadData : DataEvent()

    object UploadDataFromDatabase : DataEvent()

    data class LoadDataIntoDatabase(val detailedForecastList: List<DetailedForecastModel>) : DataEvent()

    data class OnSuccessForecastRequest(val detailedForecastList: List<DetailedForecastModel>) : DataEvent()
}

sealed class SingleEvent: Event {

    data class ShowToast(val errorMessage: String) : SingleEvent()
}
