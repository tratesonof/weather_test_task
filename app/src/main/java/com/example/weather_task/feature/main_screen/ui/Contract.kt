package com.example.weather_task.feature.main_screen.ui

import androidx.annotation.StringRes
import com.example.weather_task.base.utils.Event
import com.example.weather_task.database.repository.LocalCityForecastModel
import com.example.weather_task.feature.main_screen.domain.model.CityForecastModel

data class ViewState(
    val isLoading: Boolean,
    val chosenCities: List<CityForecastModel>
)

sealed class UiEvent : Event {

    object OnAddCityClicked : UiEvent()

    data class OnAddCityConfirmed(val city: String) : UiEvent()
}

sealed class DataEvent : Event {

    object DownloadDataFromDb : DataEvent()

    data class ProcessDbData(val dbList: List<LocalCityForecastModel>) : DataEvent()

    object DownloadDataFromApi : DataEvent()

}

sealed class SingleEvent : Event {

    data class ShowAddCityDialogue(@StringRes val message: Int) : SingleEvent()

    data class ShowToast(val errorMessage: String) : SingleEvent()
}
