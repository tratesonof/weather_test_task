package com.example.weather_task.feature.detailed_forecast.ui

import androidx.lifecycle.viewModelScope
import com.example.weather_task.base.utils.BaseViewModel
import com.example.weather_task.base.utils.Event
import com.example.weather_task.base.utils.SingleLiveEvent
import com.example.weather_task.database.repository.DetailedForecastRepository
import com.example.weather_task.database.repository.LocalCityDetailedForecastModel
import com.example.weather_task.feature.detailed_forecast.domain.DetailedForecastInteractor
import com.example.weather_task.feature.detailed_forecast.domain.model.DetailedForecastModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedForecastViewModel(
    private val detailedForecastRepository: DetailedForecastRepository,
    private val detailedForecastInteractor: DetailedForecastInteractor,
    private val city: String
) :
    BaseViewModel<ViewState>() {

    val singleLiveEvent = SingleLiveEvent<SingleEvent>()

    init {
        processUiEvent(UiEvent.GetDetailedForecast)
    }

    override fun initialViewState(): ViewState {
        return ViewState(listOf(), city, false)
    }

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.GetDetailedForecast -> {

                processDataEvent(DataEvent.LoadData)

                viewModelScope.launch(Dispatchers.IO) {
                    detailedForecastInteractor.getDetailedCityForecast(city).fold(
                        onSuccess = {
                            processDataEvent(DataEvent.OnSuccessForecastRequest(it))
                        },
                        onError = {
                            singleLiveEvent.postValue(SingleEvent.ShowToast(it.localizedMessage ?: "Error occurred"))

                            processDataEvent(DataEvent.UploadDataFromDatabase)
                        }
                    )
                }
            }

            is DataEvent.UploadDataFromDatabase -> {
                detailedForecastRepository.getDetailedForecast().fold(
                    onSuccess = {
                        if (it.isNotEmpty()) {
                            return previousState.copy(
                                detailedForecastList = it.filter { it1 -> it1.city == city }.toDomain(),
                                isLoading = false
                            )
                        }

                    },
                    onFailure = {

                    }
                )
            }

            is DataEvent.LoadData -> {
                return previousState.copy(isLoading = true)
            }

            is DataEvent.LoadDataIntoDatabase -> {
                detailedForecastRepository.putDetailedForecast(event.detailedForecastList.toLocal())
            }

            is DataEvent.OnSuccessForecastRequest -> {
                processDataEvent(DataEvent.LoadDataIntoDatabase(event.detailedForecastList))

                return previousState.copy(
                    detailedForecastList = event.detailedForecastList,
                    isLoading = false
                )
            }
        }

        return null
    }

    private fun List<LocalCityDetailedForecastModel>.toDomain(): List<DetailedForecastModel> {
        return this.map {
            DetailedForecastModel(temp = it.temp, datetime = it.datetime)
        }
    }

    private fun List<DetailedForecastModel>.toLocal(): List<LocalCityDetailedForecastModel> {
        return this.map {
            LocalCityDetailedForecastModel(city = city, temp = it.temp, datetime = it.datetime)
        }
    }
}
