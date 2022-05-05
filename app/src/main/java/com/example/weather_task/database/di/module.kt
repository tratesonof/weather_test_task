package com.example.weather_task.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weather_task.database.AppDatabase
import com.example.weather_task.database.repository.DetailedForecastRepository
import com.example.weather_task.database.repository.DetailedForecastRepositoryImpl
import com.example.weather_task.database.repository.ForecastRepository
import com.example.weather_task.database.repository.ForecastRepositoryImpl
import com.example.weather_task.feature.main_screen.data.api.ForecastRepoImpl
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module

val databaseModule = module {
    single(createdAtStart = true) {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, AppDatabase.DATABASE_NAME
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            }).build()
    }

    single { get<AppDatabase>().forecastDao() }

    single { get<AppDatabase>().detailedForecastDao() }

    single {
        ForecastRepositoryImpl(forecastDao = get())
    } binds arrayOf(ForecastRepository::class)

    single {
        DetailedForecastRepositoryImpl(detailedForecastDao = get())
    } binds arrayOf(DetailedForecastRepository::class)
}
