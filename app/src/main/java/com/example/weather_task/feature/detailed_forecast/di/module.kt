package com.example.weather_task.feature.detailed_forecast.di

import com.example.weather_task.database.repository.DetailedForecastRepository
import com.example.weather_task.feature.detailed_forecast.data.api.DetailedForecastApi
import com.example.weather_task.feature.detailed_forecast.data.api.DetailedForecastRemoteSource
import com.example.weather_task.feature.detailed_forecast.data.api.DetailedForecastRepo
import com.example.weather_task.feature.detailed_forecast.data.api.DetailedForecastRepoImpl
import com.example.weather_task.feature.detailed_forecast.domain.DetailedForecastInteractor
import com.example.weather_task.feature.detailed_forecast.ui.DetailedForecastViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val detailedForecastModule = module {

    single<DetailedForecastApi> {
        get<Retrofit>().create(DetailedForecastApi::class.java)
    }

    single<DetailedForecastRemoteSource> {
        DetailedForecastRemoteSource(get<DetailedForecastApi>())
    }

    single<DetailedForecastRepo> {
        DetailedForecastRepoImpl(get<DetailedForecastRemoteSource>())
    }

    single<DetailedForecastInteractor> {
        DetailedForecastInteractor(get<DetailedForecastRepo>())
    }

    viewModel<DetailedForecastViewModel> { (city: String) ->
        DetailedForecastViewModel(
            get<DetailedForecastRepository>(),
            get<DetailedForecastInteractor>(),
            city
        )
    }
}
