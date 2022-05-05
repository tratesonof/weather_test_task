package com.example.weather_task.feature.main_screen.ui

import androidx.annotation.StringRes
import com.example.weather_task.base.utils.Event
import com.example.weather_task.feature.main_screen.domain.model.CityForecastModel

data class ViewState(
    val forecastList: List<CityForecastModel>,
    val isLoading: Boolean
)

sealed class UiEvent : Event {
    object GetForecasts : UiEvent()

    object OnAddCityClicked : UiEvent()

    data class OnAddCityConfirmed(val city: String) : UiEvent()

}

sealed class DataEvent : Event {
    object LoadData : DataEvent()

    object LoadDataFromDatabase: DataEvent()

    object DataLoaded : DataEvent()

    data class OnSuccessDatabaseUploading(val databaseList: List<CityForecastModel>) : DataEvent()

    data class OnSuccessForecastRequest(val cityForecast: CityForecastModel) : DataEvent()

}

sealed class SingleEvent : Event {

    data class ShowAddCityDialogue(@StringRes val title: Int) : SingleEvent()

    data class ShowToast(val errorMessage: String) : SingleEvent()
}
