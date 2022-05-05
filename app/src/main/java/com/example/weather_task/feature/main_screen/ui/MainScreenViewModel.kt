package com.example.weather_task.feature.main_screen.ui

import androidx.lifecycle.viewModelScope
import com.example.weather_task.R
import com.example.weather_task.base.utils.BaseViewModel
import com.example.weather_task.base.utils.Event
import com.example.weather_task.base.utils.SingleLiveEvent
import com.example.weather_task.database.repository.ForecastRepository
import com.example.weather_task.database.repository.LocalCityForecastModel
import com.example.weather_task.feature.main_screen.domain.ForecastInteractor
import com.example.weather_task.feature.main_screen.domain.model.CityForecastModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val forecastDatabase: ForecastRepository,
    private val forecastInteractor: ForecastInteractor,
    private val router: Router
) :
    BaseViewModel<ViewState>() {

    val singleLiveEvent = SingleLiveEvent<SingleEvent>()
    private var databaseList: MutableList<CityForecastModel> = mutableListOf(
        CityForecastModel(city = "Moscow", temp = "0"),
        CityForecastModel(city = "Saint Petersburg", temp = "0")
    )

    init {
        viewModelScope.launch {
            processDataEvent(DataEvent.DownloadDataFromDb)
        }
    }

    override fun initialViewState(): ViewState {
        return ViewState(
            isLoading = true,
            chosenCities = listOf()
        )
    }

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnAddCityClicked -> {
                singleLiveEvent.value = SingleEvent.ShowAddCityDialogue(message = R.string.text_type_in_new_city_name)
            }

            is UiEvent.OnAddCityConfirmed -> {
                forecastInteractor.getCityForecast(event.city).fold(
                    onSuccess = {
                        val found = databaseList.find { elem -> elem.city == it.city }

                        if (found == null) {
                            databaseList.add(it)
                        } else {
                            databaseList.apply {
                                remove(found)
                                add(it)
                            }
                        }
                    },
                    onError = {
                        singleLiveEvent.value =
                            SingleEvent.ShowToast(
                                it.localizedMessage ?: "Error occurred while gettind data from api"
                            )
                    }
                )

                return previousState.copy(chosenCities = databaseList.toList())
            }

            is DataEvent.DownloadDataFromDb -> {

                forecastDatabase.getForecasts().fold(
                    onSuccess = {
                        processDataEvent(DataEvent.ProcessDbData(it))
                    },
                    onFailure = {
                        singleLiveEvent.value =
                            SingleEvent.ShowToast(
                                it.localizedMessage ?: "Error occurred while gettind data from db"
                            )
                    }
                )
            }

            is DataEvent.ProcessDbData -> {

                if (event.dbList.isNotEmpty()) {
                    databaseList = event.dbList.toDomain().toMutableList()
                }

                processDataEvent(DataEvent.DownloadDataFromApi)
            }

            is DataEvent.DownloadDataFromApi -> {
                val listToRemove: MutableList<CityForecastModel> = mutableListOf()
                val listToAdd: MutableList<CityForecastModel> = mutableListOf()

                for (item in databaseList) {
                    forecastInteractor.getCityForecast(item.city).fold(
                        onSuccess = {
                            val found = databaseList.find { elem -> elem.city == it.city }

                            if (found == null) {
                                listToAdd.add(it)
                            } else {
                                listToRemove.add(found)
                                listToAdd.add(it)
                            }
                        },
                        onError = {
                            singleLiveEvent.value =
                                SingleEvent.ShowToast(
                                    it.localizedMessage ?: "Error occurred while gettind data from api"
                                )
                        }
                    )
                }

                databaseList.apply {
                    addAll(listToAdd)
                    removeAll(listToRemove)
                }

                return previousState.copy(chosenCities = databaseList.toList(), isLoading = false)
            }
        }

        return null
    }

    private fun List<LocalCityForecastModel>.toDomain(): List<CityForecastModel> {
        return this.map {
            CityForecastModel(temp = it.temp, city = it.city)
        }
    }
}
