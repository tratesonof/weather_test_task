package com.example.weather_task.feature.main_screen.ui

import com.example.weather_task.R
import com.example.weather_task.base.utils.BaseViewModel
import com.example.weather_task.base.utils.Event
import com.example.weather_task.base.utils.SingleLiveEvent
import com.example.weather_task.database.repository.ForecastRepository
import com.example.weather_task.database.repository.LocalCityForecastModel
import com.example.weather_task.feature.main_screen.domain.ForecastInteractor
import com.example.weather_task.feature.main_screen.domain.model.CityForecastModel
import com.github.terrakok.cicerone.Router

class MainScreenViewModel(
    private val forecastDatabase: ForecastRepository,
    private val forecastInteractor: ForecastInteractor,
    private val router: Router
) :
    BaseViewModel<ViewState>() {

    private var databaseMap: MutableMap<String, CityForecastModel> = mutableMapOf()
    private val citiesMutableSet: MutableSet<String> = mutableSetOf("Moscow", "Saint Petersburg")
    val singleLiveEvent = SingleLiveEvent<SingleEvent>()

    init {
        processDataEvent(DataEvent.LoadDataFromDatabase)

        processUiEvent(UiEvent.GetForecasts)
    }

    override fun initialViewState(): ViewState {
        return ViewState(listOf(), false)
    }

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.GetForecasts -> {
                processDataEvent(DataEvent.LoadData)

                for (city in citiesMutableSet.toList()) {
                    getCityForecast(city)
                }

                processDataEvent(DataEvent.DataLoaded)
            }

            is UiEvent.OnAddCityClicked -> {
                singleLiveEvent.value = SingleEvent.ShowAddCityDialogue(
                    title = R.string.text_type_in_new_city_name
                )
            }

            is UiEvent.OnAddCityConfirmed -> {
                getCityForecast(event.city)
            }

            is DataEvent.LoadData -> {
                return previousState.copy(isLoading = true)
            }

            is DataEvent.DataLoaded -> {
                println(databaseMap.values.toList())
                return previousState.copy(isLoading = false, forecastList = databaseMap.values.toList())
            }

            is DataEvent.OnSuccessDatabaseUploading -> {

                for (item in event.databaseList) {
                    databaseMap[item.city] = item
                    citiesMutableSet.add(item.city)
                }
            }

            is DataEvent.LoadDataFromDatabase -> {
                forecastDatabase.getForecasts().fold(
                    onSuccess = {
                        processDataEvent(DataEvent.OnSuccessDatabaseUploading(it.toDomain()))
                    },
                    onFailure = {
                        singleLiveEvent.value = SingleEvent.ShowToast(it.localizedMessage ?: "Error occurred")
                    }
                )
            }

            is DataEvent.OnSuccessForecastRequest -> {
                val tempCityForecast = event.cityForecast
                forecastDatabase.putForecast(tempCityForecast.toLocal())
                databaseMap.remove(tempCityForecast.city)
                databaseMap[tempCityForecast.city] = tempCityForecast
            }
        }

        return null
    }

    private suspend fun getCityForecast(city: String) {

        forecastInteractor.getCityForecast(city).fold(
            onSuccess = {
                processDataEvent(DataEvent.OnSuccessForecastRequest(it))
            },
            onError = {
                singleLiveEvent.postValue(SingleEvent.ShowToast(it.localizedMessage ?: "Error occurred"))
            }
        )
    }

    private fun CityForecastModel.toLocal(): LocalCityForecastModel =
        LocalCityForecastModel(city = this.city, temp = this.temp)

    private fun List<LocalCityForecastModel>.toDomain(): List<CityForecastModel> {
        return this.map {
            CityForecastModel(city = it.city, temp = it.temp)
        }
    }
}
