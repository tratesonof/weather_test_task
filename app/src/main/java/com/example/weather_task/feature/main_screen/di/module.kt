package com.example.weather_task.feature.main_screen.di

import com.example.weather_task.database.repository.ForecastRepository
import com.example.weather_task.feature.main_screen.data.api.ForecastApi
import com.example.weather_task.feature.main_screen.data.api.ForecastRemoteSource
import com.example.weather_task.feature.main_screen.data.api.ForecastRepo
import com.example.weather_task.feature.main_screen.data.api.ForecastRepoImpl
import com.example.weather_task.feature.main_screen.domain.ForecastInteractor
import com.example.weather_task.feature.main_screen.ui.MainScreenViewModel
import com.github.terrakok.cicerone.Router
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mainScreenModule = module {

    single<ForecastApi> {
        get<Retrofit>().create(ForecastApi::class.java)
    }

    single<ForecastRemoteSource> {
        ForecastRemoteSource(get<ForecastApi>())
    }

    single<ForecastRepo> {
        ForecastRepoImpl(get<ForecastRemoteSource>())
    }

    single<ForecastInteractor> {
        ForecastInteractor(get<ForecastRepo>())
    }

    viewModel<MainScreenViewModel> {
        MainScreenViewModel(get<ForecastRepository>(), get<ForecastInteractor>(), get<Router>())
    }
}
